package com.example.privateclub.controller;


import com.example.privateclub.UserDTO;
import com.example.privateclub.exceptions.NotFoundException;
import com.example.privateclub.repository.User;
import com.example.privateclub.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers(){
        return this.userService.getUsers();
    }

    @GetMapping("/{uuid}")
    public UserDTO getUserByUUID(@PathVariable UUID uuid) {
        return this.userService.getUserByUUID(uuid);
    }




}
