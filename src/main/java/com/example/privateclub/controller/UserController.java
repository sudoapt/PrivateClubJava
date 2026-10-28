package com.example.privateclub.controller;

import com.example.privateclub.dto.UserByQRCodeDTO;
import com.example.privateclub.dto.UserCreateAndUpdateDTO;
import com.example.privateclub.dto.UserDTO;
import com.example.privateclub.service.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/users")
public class UserController {

    private final UserService userService;

//    @Value("classpath:html/deadend.html")
//    private Resource dummyPage;

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ClassPathResource returnDummyPage() {
        return new ClassPathResource("html/deadend.html");
    }


    @GetMapping("/{uuid}")
    public ResponseEntity<UserDTO> getUserByUUID(@PathVariable UUID uuid) {
        UserDTO userDTO = this.userService.getUserByUUID(uuid);
        return ResponseEntity.ok(userDTO);
    }


    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateAndUpdateDTO userCreateAndUpdateDTO) {
        UserDTO createUserResponseDTO = userService.createNewUser(userCreateAndUpdateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUserResponseDTO);
    }


    @PutMapping("/{uuid}")
    public ResponseEntity<UserDTO> editUser(@Valid @PathVariable UUID uuid, @RequestBody UserCreateAndUpdateDTO userCreateAndUpdateDTO) {
        UserDTO updatedUser = userService.updateExistingUser(uuid, userCreateAndUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }


    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID uuid) {
        userService.deleteUserByUUID(uuid);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
