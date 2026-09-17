package com.example.privateclub.mapper;

import com.example.privateclub.dto.UserByQRCodeDTO;
import com.example.privateclub.dto.UserDTO;
import com.example.privateclub.repository.User;
import com.example.privateclub.repository.UserQRCode;

import java.util.List;
import java.util.UUID;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        List<UUID> userQRCodes = user.getUserQRCodes().stream()
                .map(UserQRCode::getUserQRCode)
                .toList();

        return new UserDTO(
                user.getUserUUID(),
                user.getUserFirstName(),
                user.getUserLastName(),
                user.getUserEmail(),
                userQRCodes);
    }

    public static UserByQRCodeDTO toByQRCodeDTO(User user) {
        return new UserByQRCodeDTO(
                user.getUserFirstName(),
                user.getUserLastName());
    }

    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setUserFirstName(userDTO.userFirstName());
        user.setUserLastName(userDTO.userLastName());
        user.setUserEmail(userDTO.userEmail());
        return user;
    }

}
