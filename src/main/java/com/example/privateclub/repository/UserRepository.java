package com.example.privateclub.repository;

import com.example.privateclub.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUserUUID(UUID uuid);

//    List<UserDTO> findAllBy();
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.userQRCodes")
    List<User> findAllUsersWithQrCodes();
}
