package com.example.privateclub.dto;

import java.util.UUID;

public record UserDTO(
                      String userFirstName,
                      String userLastName,
                      String userEmail,
                      UUID userQRCode
                      ) {
}
