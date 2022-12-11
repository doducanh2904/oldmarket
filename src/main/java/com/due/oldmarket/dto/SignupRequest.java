package com.due.oldmarket.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Set;

@Data
public class SignupRequest {
    private String name;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String sex;
    private String birthday;
    @Value("active")
    private String status ;
    private Set<String> role;
}
