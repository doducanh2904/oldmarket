package com.due.oldmarket.dto;

import com.due.oldmarket.model.File;
import com.due.oldmarket.payload.UploadFileResponse;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private List<String> roles;

    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String sex;
    private String birthday;
    private String status;

    private List<UploadFileResponse> fileList;

    public List<UploadFileResponse> getFileList() {
        return fileList;
    }

    public void setFileList(List<UploadFileResponse> fileList) {
        this.fileList = fileList;
    }

    public JwtResponse(String token, List<String> roles, Long id, String name, String username, String email, String password, String phoneNumber, String address, String sex, String birthday, String status, List<UploadFileResponse> fileList) {
        this.token = token;
        this.roles = roles;
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.sex = sex;
        this.birthday = birthday;
        this.status = status;
        this.fileList = fileList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
