package com.example.privateclub.repository;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "useruuid", updatable = false, nullable = false)
    private UUID userUUID;
    @Column(name="userfirstname", nullable = false)
    private String userFirstName;
    @Column(name = "userlastname", nullable = false)
    private String userLastName;
    @Column(name = "useremail", nullable = false)
    private String userEmail;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<UserQRCode> userQRCodes = new ArrayList<>();

    public User(String userFirstName, String userLastName, String userEmail) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
    }

    public void addQRCode(UserQRCode userQRCode){
        if (this.userQRCodes.size() >= 5) {
            throw new IllegalStateException("User can not have more than 5 qrcodes.");
        }
        // ?
        this.userQRCodes.add(userQRCode);
        userQRCode.setUser(this);
    }


    public User() {
    }

    public UUID getUserUUID() {
        return this.userUUID;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public List<UserQRCode> getUserQRCodes() {
        return userQRCodes;
    }
    public void setUserQRCodes(List<UserQRCode> qrCodes) {
        this.userQRCodes = qrCodes;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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
