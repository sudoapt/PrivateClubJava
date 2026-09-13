package com.example.privateclub.service;

import com.example.privateclub.repository.User;
import com.example.privateclub.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
