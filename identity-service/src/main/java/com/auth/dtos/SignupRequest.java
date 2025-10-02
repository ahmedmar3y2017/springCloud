package com.auth.dtos;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    private String username;
    private String password;
    private String fullNameEn;
    private String fullNameAr;
    private String email;
    private String telephone;
    private String address;
}