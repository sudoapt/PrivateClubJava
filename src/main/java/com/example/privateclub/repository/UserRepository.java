package com.example.privateclub.repository;

import com.example.privateclub.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUserQRCode(UUID qrcode);

    List<UserDTO> findAllBy();
}
