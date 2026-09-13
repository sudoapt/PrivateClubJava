package com.example.privateclub.service;

import com.example.privateclub.UserDTO;
import com.example.privateclub.exceptions.NotFoundException;
import com.example.privateclub.mapper.UserMapper;
import com.example.privateclub.repository.User;
import com.example.privateclub.repository.UserRepository;
import org.springframework.http.ResponseEntity;
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


    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public UserDTO getUserByUUID(UUID uuid) {
        User user =  userRepository.findByUserUUID(uuid);

        if (user == null) {
            throw new NotFoundException("No user found by the uuid + " + uuid);
        }

        System.out.println(UserMapper.toDTO(user));
        return UserMapper.toDTO(user);
    }

}
