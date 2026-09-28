package com.example.privateclub.repository;

import com.example.privateclub.model.User;
import com.example.privateclub.model.UserQRCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserQRCodeRepository extends JpaRepository<UserQRCode, UUID> {
//    void deleteUserQRCode(UUID userQRCode);

    @Query("SELECT u FROM User u JOIN u.userQRCodes q WHERE q.userQRCode = :qrCodeUUID")
    Optional<User> findUserByUserQRCodeUUID(@Param("qrCodeUUID") UUID qrCodeUUID);

}
