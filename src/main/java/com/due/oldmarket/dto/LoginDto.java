package com.due.oldmarket.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String  usernameOrEmail;
    private String password;
}
