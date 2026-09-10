package com.example.privateclub.repository;

import java.util.UUID;

public class User {
    private UUID userUUID;
    private String userFirstName;
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
