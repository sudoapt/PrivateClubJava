package com.example.privateclub.repository;

import com.example.privateclub.model.UserQRCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserQRCodeRepository extends JpaRepository<UserQRCode, UUID> {
//    void deleteUserQRCode(UUID userQRCode);
}
