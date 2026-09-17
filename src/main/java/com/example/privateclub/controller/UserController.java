package com.example.privateclub.controller;

import com.example.privateclub.dto.UserByQRCodeDTO;
import com.example.privateclub.dto.UserDTO;
import com.example.privateclub.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }


    @GetMapping("/{uuid}")
    public ResponseEntity<UserDTO> getUserByUUID(@PathVariable UUID uuid) {
        UserDTO userDTO = this.userService.getUserByUUID(uuid);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/qrcode/{qrcode}")
    public ResponseEntity<UserByQRCodeDTO> getUserByQRCode(@PathVariable UUID qrcode) {
        UserByQRCodeDTO userByQRCodeDTO = this.userService.readAndRotateQRCode(qrcode);
        return ResponseEntity.ok(userByQRCodeDTO);

    }


    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createUserResponseDTO = userService.createNewUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUserResponseDTO);
    }

    @PostMapping("/{uuid}/qrcodes")
    public ResponseEntity<UserDTO> makeNewQRCode(@PathVariable UUID uuid) {
        UserDTO updatedUser = userService.makeNewUserQRCode(uuid);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedUser);
    }

}
