package com.example.privateclub.dto;

import com.example.privateclub.repository.UserQRCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record UserDTO(UUID userUUID,
                      @NotBlank(message = "User must have a first name")
                      @Size(min = 2)
                      String userFirstName,
                      @NotBlank(message = "User must have a last name")
                      @Size(min = 2)
                      String userLastName,
                      @NotBlank(message = "User must have an email")
                      @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$",
                              message = "Please provide a valid email format (e.g., user@example.com)")
                      String userEmail,
                      List<UUID> userQRCodes
                      ) {
}
