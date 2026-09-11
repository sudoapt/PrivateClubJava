package com.example.privateclub.service;

import com.example.privateclub.repository.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    public List<User> getUsers() {
        return List.of(new User("John", "Doe"),
                new User("Jane", "Doe"));
    }
}
