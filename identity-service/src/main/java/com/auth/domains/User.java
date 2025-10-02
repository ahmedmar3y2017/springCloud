package com.auth.domains;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long userId;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false)
    private String password; // BCrypt encoded

    @Column(nullable = false, length = 50)
    private String role = "ROLE_USER";

    @Builder.Default
    private Boolean enabled = true;

    @Column(nullable = false, length = 200)
    private String fullNameEn;

    @Column(nullable = false, length = 200)
    private String fullNameAr;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    private String telephone;
    private String address;

    private LocalDateTime lastLoginAt;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

}
