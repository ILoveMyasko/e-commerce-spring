package com.userservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    @Column(name = "username", nullable = false, unique = true,length = 50)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    @NotBlank
    @Email
    private String email;

    @Column(name = "hashed_password", nullable = false, length = 64) // how much?
    @NotBlank
    private String hashedPassword;

    //TODO role? auth, enum role_admin/role_user?

}