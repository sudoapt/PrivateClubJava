package com.example.privateclub.controller;

import com.example.privateclub.dto.UserByQRCodeDTO;
import com.example.privateclub.dto.UserCreateAndUpdateDTO;
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

    @GetMapping(produces = "text/html")
    public String returnDummyPage() {
        return """
               <html>
               <body style="font-family: sans-serif; display: flex; justify-content: center; align-items: center; height: 80vh; margin: 0; background-color: #f9f9f9;">
                   <blockquote style="font-size: 1.8rem; font-style: italic; max-width: 600px; text-align: center; color: #333; line-height: 1.5;">
                       "One can not simply return all the records with no pagination..."
                       <cite style="display: block; font-size: 1rem; font-style: normal; color: #777; margin-top: 15px; font-weight: bold;">
                           &copy; houston517
                       </cite>
                   </blockquote>
               </body>
               </html>
               """;
    }


    @GetMapping("/{uuid}")
    public ResponseEntity<UserDTO> getUserByUUID(@PathVariable UUID uuid) {
        UserDTO userDTO = this.userService.getUserByUUID(uuid);
        return ResponseEntity.ok(userDTO);
    }



    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreateAndUpdateDTO userCreateAndUpdateDTO) {
        UserDTO createUserResponseDTO = userService.createNewUser(userCreateAndUpdateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUserResponseDTO);
    }


    @PutMapping("/{uuid}")
    public ResponseEntity<UserDTO> editUser(@PathVariable UUID uuid, @RequestBody UserCreateAndUpdateDTO userCreateAndUpdateDTO) {
        UserDTO updatedUser = userService.updateExistingUser(uuid, userCreateAndUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }


    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID uuid) {
        userService.deleteUserByUUID(uuid);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
