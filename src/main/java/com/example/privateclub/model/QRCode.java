package com.example.privateclub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "qrcodes")
@NoArgsConstructor
public class QRCode {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "qrcodeuuid", updatable = false, nullable = false)
    private UUID qrcodeuuid;

    @Setter
    @Getter
    @Column(name = "qrcode", nullable = true)
    private UUID QRCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "useruuid", nullable = false)
    @JsonIgnore
    @Getter
    private User user;


    public void setUser(User user) {
        this.user = user;
        if (user != null && !user.getQrCodes().contains(this)) {
            user.getQrCodes().add(this);
        }
    }
}
