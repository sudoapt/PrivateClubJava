package com.example.privateclub.service;

import com.example.privateclub.dto.UserDTO;
import com.example.privateclub.exceptions.NotFoundException;
import com.example.privateclub.mapper.UserMapper;
import com.example.privateclub.repository.User;
import com.example.privateclub.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    // bean injection
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<UserDTO> getUsers() {
        return userRepository.findAllBy();
    }

    public UserDTO getUserByUUID(UUID qrcode) {
        User user =  userRepository.findByUserQRCode(qrcode);

        if (user == null) {
            throw new NotFoundException("No user found by this qrcode + " + qrcode);
        }

        System.out.println(UserMapper.toDTO(user));
        return UserMapper.toDTO(user);
    }

}
