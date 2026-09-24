package com.example.privateclub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "user_qrcodes")
@NoArgsConstructor
public class UserQRCode {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "userqrcode")
    private UUID userQRCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "useruuid", nullable = false)
    @JsonIgnore
    @Getter
    private User user;


    public void setUser(User user) {
        this.user = user;
        if (user != null && !user.getUserQRCodes().contains(this)) {
            user.getUserQRCodes().add(this);
        }
    }
}
