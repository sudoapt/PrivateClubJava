package com.example.privateclub.dto;

import com.example.privateclub.repository.UserQRCode;

import java.util.List;
import java.util.UUID;

public record UserDTO(UUID userUUID,
                      String userFirstName,
                      String userLastName,
                      String userEmail,
                      List<UUID> userQRCodes
                      ) {
}
