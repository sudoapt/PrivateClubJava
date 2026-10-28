package com.example.privateclub.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "useruuid", updatable = false, nullable = false)
    private UUID userUUID;
    @Setter
    @Column(name="userfirstname", nullable = false)
    private String userFirstName;
    @Setter
    @Column(name = "userlastname", nullable = false)
    private String userLastName;
    @Setter
    @Column(name = "useremail", nullable = false)
    private String userEmail;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<QRCode> qrCodes = new ArrayList<>();



    public void addQRCode(QRCode qrCode){
        this.qrCodes.add(qrCode);
        qrCode.setUser(this);
    }


    @Override
    public String toString() {
        return "User{" +
                "userUUID=" + userUUID +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}
