package com.example.privateclub.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

    public User(String userFirstName, String userLastName) {
        this.userUUID = UUID.randomUUID();
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
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
}
