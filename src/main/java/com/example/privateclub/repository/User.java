package com.example.privateclub.repository;

import jakarta.persistence.*;

import java.util.UUID;


@Entity
@Table(name = "users")
public class User {

    @Id
    private UUID userUUID;
    @Column(name="userfirstname", nullable = false)
    private String userFirstName;
    @Column(name = "userlastname", nullable = false)
    private String userLastName;
    @Column(name = "useremail", nullable = false)
    private String userEmail;
    @Column(name = "userqrcode", nullable = false)
    private UUID userQRCode;

    public User(String userFirstName, String userLastName, String userEmail) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
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

    public UUID getUserQRCode() {
        return userQRCode;
    }

    public void setUserQRCode(UUID userQRCode) {
        this.userQRCode = userQRCode;
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
                ", userQRCode=" + userQRCode +
                '}';
    }
}
