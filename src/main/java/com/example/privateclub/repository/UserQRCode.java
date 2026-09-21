package com.example.privateclub.repository;

import com.example.privateclub.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "user_qrcodes")
public class UserQRCode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "userqrcode")
    private UUID userQRCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "useruuid", nullable = false)
    @JsonIgnore
    private User user;


    public UserQRCode() {}

    public UUID getUserQRCode() {
        return userQRCode;
    }

    public void setUserQRCode(UUID userQRCode) {
        this.userQRCode = userQRCode;
    }


    public void setUser(User user) {
        this.user = user;
    }
}
