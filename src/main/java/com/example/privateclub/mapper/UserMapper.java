package com.example.privateclub.mapper;

import com.example.privateclub.UserDTO;
import com.example.privateclub.repository.User;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getUserUUID(),
                user.getUserFirstName(),
                user.getUserLastName());
    }

    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setUserFirstName(userDTO.userFirstName());
        user.setUserLastName(userDTO.userLastName());
        return user;
    }

}
