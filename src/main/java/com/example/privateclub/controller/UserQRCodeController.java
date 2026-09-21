package com.example.privateclub.controller;

import com.example.privateclub.dto.UserByQRCodeDTO;
import com.example.privateclub.dto.UserDTO;
import com.example.privateclub.service.UserQRCodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public class UserQRCodeController {

    private final UserQRCodeService userQRCodeService;

    public UserQRCodeController(UserQRCodeService userQRCodeService) {
        this.userQRCodeService = userQRCodeService;
    }

    @GetMapping("/qrcode/{qrcode}")
    public ResponseEntity<UserByQRCodeDTO> getUserByQRCode(@PathVariable UUID qrcode) {
        UserByQRCodeDTO userByQRCodeDTO = userQRCodeService.readAndRotateQRCode(qrcode);
        return ResponseEntity.ok(userByQRCodeDTO);

    }

    @PostMapping("/{uuid}/qrcodes")
    public ResponseEntity<UserDTO> makeNewQRCode(@PathVariable UUID uuid) {
        UserDTO updatedUser = userQRCodeService.makeNewUserQRCode(uuid);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedUser);
    }

    @PutMapping("/{uuid}/qrcodes/{qrcode}")
    public ResponseEntity<UserDTO> editQRCode(@PathVariable UUID uuid, @PathVariable("qrcode") UUID userQRCodeUUID) {
        UserDTO updatedUser =  userQRCodeService.editUserQRCode(uuid, userQRCodeUUID);

        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @DeleteMapping("/{uuid}/qrcodes/{qrcode}")
    public ResponseEntity<Void> deleteUserQRCode(@PathVariable UUID uuid, @PathVariable("qrcode") UUID userQRCodeUUID) {
        userQRCodeService.deleteUserQRCode(uuid, userQRCodeUUID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }


}
