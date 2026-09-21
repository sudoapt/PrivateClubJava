package com.example.privateclub.model;

import com.example.privateclub.repository.UserQRCode;
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
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<UserQRCode> userQRCodes = new ArrayList<>();

    public User(String userFirstName, String userLastName, String userEmail) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
    }

    public void addQRCode(UserQRCode userQRCode){
//        if (this.userQRCodes.size() >= 5) {
//            throw new IllegalStateException("User can not have more than 5 qrcodes.");
//        }
        this.userQRCodes.add(userQRCode);
        userQRCode.setUser(this);
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
