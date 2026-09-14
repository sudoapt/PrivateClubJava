package com.example.privateclub.mapper;

import com.example.privateclub.dto.UserDTO;
import com.example.privateclub.repository.User;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        return new UserDTO(
//                user.getUserUUID(),
                user.getUserFirstName(),
                user.getUserLastName(),
                user.getUserEmail(),
                user.getUserQRCode());
    }

    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setUserFirstName(userDTO.userFirstName());
        user.setUserLastName(userDTO.userLastName());
        user.setUserEmail(userDTO.userEmail());
        return user;
    }

}
