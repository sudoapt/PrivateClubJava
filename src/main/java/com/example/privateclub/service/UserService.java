package com.example.privateclub.service;

import com.example.privateclub.dto.UserCreateAndUpdateDTO;
import com.example.privateclub.dto.UserDTO;
import com.example.privateclub.exceptions.NotFoundException;
import com.example.privateclub.mapper.UserMapper;
import com.example.privateclub.model.User;
import com.example.privateclub.model.UserQRCode;
import com.example.privateclub.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {
    // bean injection
    private final UserRepository userRepository;

//    @PersistenceContext
//    private EntityManager entityManager;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUserByUUID(UUID uuid) {
        User user = userRepository.findByUserUUID(uuid);

        if (user == null) {
            throw new NotFoundException("ERROR: No user found by this uuid + " + uuid);
        }
        return UserMapper.toDTO(user);
    }

    // TODO: custom exception handler
    @Transactional
    public UserDTO createNewUser(UserCreateAndUpdateDTO userCreateAndUpdateDTO) {
        User user = UserMapper.toEntityFromCreate(userCreateAndUpdateDTO);

        UserQRCode userQRCode = new UserQRCode();
        user.addQRCode(userQRCode);


        User savedUser = userRepository.save(user);

//        userRepository.flush();

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
