package com.example.privateclub.service;

import com.example.privateclub.dto.UserByQRCodeDTO;
import com.example.privateclub.dto.UserCreateAndUpdateDTO;
import com.example.privateclub.dto.UserDTO;
import com.example.privateclub.exceptions.EntityNotFoundException;
import com.example.privateclub.exceptions.MaxLimitExceededException;
import com.example.privateclub.exceptions.NotFoundException;
import com.example.privateclub.mapper.UserMapper;
import com.example.privateclub.repository.User;
import com.example.privateclub.repository.UserQRCode;
import com.example.privateclub.repository.UserQRCodeRepository;
import com.example.privateclub.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    // bean injection
    private final UserRepository userRepository;
    private final UserQRCodeRepository userQRCodeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public UserService(UserRepository userRepository, UserQRCodeRepository userQRCodeRepository) {
        this.userRepository = userRepository;
        this.userQRCodeRepository = userQRCodeRepository;
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAllUsersWithQrCodes();

        return users.stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    @Transactional
    public UserByQRCodeDTO readAndRotateQRCode(UUID userQRCodeUUID) {
        User user = userRepository.findUserByUserQRCodeUUID(userQRCodeUUID)
                .orElseThrow(() -> new EntityNotFoundException("Invalid or expired QR code: " + userQRCodeUUID));

        // remove the qrcode from the List<UserQRCode> userQRCodes
        user.getUserQRCodes().removeIf(qrcode -> qrcode.getUserQRCode().equals(userQRCodeUUID));

        // delete the qrcode from the db
        userQRCodeRepository.deleteByUserQRCode(userQRCodeUUID);

        userRepository.save(user);
        userRepository.flush();
        entityManager.refresh(user);

        UserQRCode newQRCode = new UserQRCode();
        // writes the new qr to the user we're working on
        newQRCode.setUser(user);
        // inserts a new qrcode to the table
        userQRCodeRepository.save(newQRCode);

        return UserMapper.toByQRCodeDTO(user);
    }

    @Transactional
    public UserDTO makeNewUserQRCode(UUID uuid) {
        User user = userRepository.findByUserUUID(uuid);
        if (user == null) {
            throw new NotFoundException("No user found by this uuid + " + uuid);
        }

        if (user.getUserQRCodes().size() >= 5) {
            throw new MaxLimitExceededException("FORBIDDEN: 5 QR codes per user is the limit.");
        }

        UserQRCode newUserQRCode = new UserQRCode();
        newUserQRCode.setUser(user);
        userQRCodeRepository.save(newUserQRCode);

        userRepository.flush();
        entityManager.refresh(user);

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
                .orElseThrow(() -> new NotFoundException("ERROR: No user found by this uuid + " + userUUID));

        if (!user.getUserQRCodes().contains(userQRCode)) {
            throw new IllegalArgumentException("ERROR: This QR code does not belong to the specified user.");
        }

        // delete from java memory
        user.getUserQRCodes().remove(userQRCode);
        // delete from the db
        userQRCodeRepository.deleteByUserQRCode(userQRCodeUUID);

        userRepository.save(user);
        userRepository.flush();

    }


    public UserDTO getUserByUUID(UUID uuid) {
        User user = userRepository.findByUserUUID(uuid);

        if (user == null) {
            throw new NotFoundException("ERROR: No user found by this uuid + " + uuid);
        }
        return UserMapper.toDTO(user);
    }


    @Transactional
    public UserDTO createNewUser(UserCreateAndUpdateDTO userCreateAndUpdateDTO) {
        User user = UserMapper.toEntityFromCreate(userCreateAndUpdateDTO);

        for (int i = 0; i < 5; i++) {
            UserQRCode userQRCode = new UserQRCode();
            user.addQRCode(userQRCode);
        }

        User savedUser = userRepository.save(user);

//        userRepository.flush();
//        entityManager.refresh(savedUser);

        return UserMapper.toDTO(savedUser);
    }

    @Transactional
    public UserDTO updateExistingUser(UUID uuid, UserCreateAndUpdateDTO userCreateAndUpdateDTO) {
        User user = userRepository.findByUserUUID(uuid);

        if (user == null) {
            throw new NotFoundException("ERROR: No user found by this uuid + " + uuid);
        }

        user.setUserFirstName(userCreateAndUpdateDTO.userFirstName());
        user.setUserLastName(userCreateAndUpdateDTO.userLastName());
        user.setUserEmail(userCreateAndUpdateDTO.userEmail());

        User updatedUser = userRepository.save(user);

        return UserMapper.toDTO(updatedUser);
    }

    @Transactional
    public void deleteUserByUUID(UUID uuid) {

        if (!userRepository.existsById(uuid)) {
            throw new NotFoundException("ERROR: No user found by this uuid + " + uuid);
        }

        userRepository.deleteById(uuid);
    }

}
