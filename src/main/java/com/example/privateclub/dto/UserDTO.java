package com.example.privateclub;

import java.util.UUID;

public record UserDTO(UUID uuid, String userFirstName, String userLastName) {
}
