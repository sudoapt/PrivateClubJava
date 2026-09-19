package com.example.privateclub.dto;

public record UserCreateAndUpdateDTO(
        String userFirstName,
        String userLastName,
        String userEmail
) {
}
