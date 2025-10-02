package com.auth.dtos;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long userId;
    private String username;
    private String fullNameEn;
    private String fullNameAr;
    private String email;
    private String telephone;
    private String address;

}