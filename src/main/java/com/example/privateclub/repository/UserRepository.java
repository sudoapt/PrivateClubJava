package com.example.privateclub.repository;

import com.example.privateclub.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUserUUID(UUID uuid);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.userQRCodes")
    List<User> findAllUsersWithQrCodes();

    @Query("SELECT u FROM User u JOIN u.userQRCodes q WHERE q.userQRCode = :qrCodeUUID")
    Optional<User> findUserByUserQRCodeUUID(@Param("qrCodeUUID") UUID qrCodeUUID);

}
