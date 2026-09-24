package com.example.privateclub.service;

import com.example.privateclub.dto.UserByQRCodeDTO;
import com.example.privateclub.dto.UserDTO;
import com.example.privateclub.exceptions.EntityNotFoundException;
import com.example.privateclub.exceptions.NotFoundException;
import com.example.privateclub.mapper.UserMapper;
import com.example.privateclub.model.User;
import com.example.privateclub.model.UserQRCode;
import com.example.privateclub.repository.UserQRCodeRepository;
import com.example.privateclub.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserQRCodeService {
    private final UserRepository userRepository;
    private final UserQRCodeRepository userQRCodeRepository;

    public UserQRCodeService(UserRepository userRepository, UserQRCodeRepository userQRCodeRepository) {
        this.userRepository = userRepository;
        this.userQRCodeRepository = userQRCodeRepository;
    }

    @Transactional
    public UserByQRCodeDTO readAndRotateQRCode(UUID userQRCodeUUID) {
        User user = userRepository.findUserByUserQRCodeUUID(userQRCodeUUID)
                .orElseThrow(() -> new EntityNotFoundException("Invalid or expired QR code: " + userQRCodeUUID));

        this.deleteUserQRCode(user.getUserUUID(), userQRCodeUUID);

        UserQRCode newQRCode = new UserQRCode();
        // writes the new qr to the user we're working on
        newQRCode.setUser(user);

        // update java memory - add 1 back to list
        user.getUserQRCodes().add(newQRCode);

        // inserts a new qrcode to the table
        userRepository.save(user);
        userRepository.flush();

        return UserMapper.toByQRCodeDTO(user);
    }

    @Transactional
    public UserDTO makeNewUserQRCode(UUID uuid) {
        User user = userRepository.findByUserUUID(uuid);
        if (user == null) {
            throw new NotFoundException("No user found by this uuid + " + uuid);
        }

        UserQRCode newUserQRCode = new UserQRCode();
        newUserQRCode.setUser(user);
        userQRCodeRepository.save(newUserQRCode);

        userRepository.flush();

        return UserMapper.toDTO(user);
    }


    @Transactional
    public void deleteUserQRCode(UUID userUUID, UUID userQRCodeUUID) {
        User user = userRepository.findByUserUUID(userUUID);

        if (user == null) {
            throw new NotFoundException("ERROR: No user found by this uuid + " + userUUID);
        }
        // find the qrcode and check if it belongs to this user
        UserQRCode userQRCode = userQRCodeRepository.findById(userQRCodeUUID)
                .orElseThrow(() -> new NotFoundException("ERROR: No user found has this qrcode + " + userQRCodeUUID));

        if (!user.getUserQRCodes().contains(userQRCode)) {
            throw new IllegalArgumentException("ERROR: This QR code does not belong to the specified user.");
        }

        // delete from java memory
        user.getUserQRCodes().remove(userQRCode);
        // delete from the db
        userQRCodeRepository.deleteById(userQRCodeUUID);

        userRepository.save(user);
        userRepository.flush();

    }

    @Transactional
    public UserDTO editUserQRCode(UUID userUUID, UUID userQRCodeUUID) {
        deleteUserQRCode(userUUID, userQRCodeUUID);

        User user = userRepository.findByUserUUID(userUUID);
        UserQRCode newQRCode = new UserQRCode();
        newQRCode.setUser(user);
        userQRCodeRepository.save(newQRCode);

        userRepository.save(user);
        userRepository.flush();

        return UserMapper.toDTO(user);
    }


}
