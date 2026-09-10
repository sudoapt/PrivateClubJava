package com.example.privateclub.controller;


import com.example.privateclub.repository.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @GetMapping
    public List<User> users() {
        return List.of(new User("John", "Doe"),
                new User("Jane", "Doe"));
    }
}
