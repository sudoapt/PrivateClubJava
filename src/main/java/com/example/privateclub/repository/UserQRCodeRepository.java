package com.example.privateclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserQRCodeRepository extends JpaRepository<UserQRCode, UUID> {
    void deleteByUserQRCode(UUID userQRCode);
}
