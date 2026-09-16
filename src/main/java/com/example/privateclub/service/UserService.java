package com.example.privateclub.service;

import com.example.privateclub.dto.UserDTO;
import com.example.privateclub.exceptions.EntityNotFoundException;
import com.example.privateclub.exceptions.NotFoundException;
import com.example.privateclub.mapper.UserMapper;
import com.example.privateclub.repository.User;
import com.example.privateclub.repository.UserQRCode;
import com.example.privateclub.repository.UserQRCodeRepository;
import com.example.privateclub.repository.UserRepository;
import jakarta.persistence.Entity;
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
    public UserDTO readAndRotateQRCode(UUID userQRCodeUUID) {
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
        newQRCode.setUser(user);
        userQRCodeRepository.save(newQRCode);

        return UserMapper.toDTO(user);
    }



    public UserDTO getUserByUUID(UUID uuid) {
        User user =  userRepository.findByUserUUID(uuid);

        if (user == null) {
            throw new NotFoundException("No user found by this uuid + " + uuid);
        }

        System.out.println(UserMapper.toDTO(user));
        return UserMapper.toDTO(user);
    }




    @Transactional
    public UserDTO createNewUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);

        for (int i = 0; i < 5; i++) {
            UserQRCode userQRCode = new UserQRCode();
            user.addQRCode(userQRCode);
        }

        User savedUser = userRepository.save(user);

//        userRepository.flush();
//        entityManager.refresh(savedUser);

        return UserMapper.toDTO(savedUser);
    }

}
