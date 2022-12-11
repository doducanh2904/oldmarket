package com.due.oldmarket.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class UserDTO {
    private Long idUser;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private String sex;
    private String birthday;
    private String status;
    private Set<String> urlImageSet;

}
