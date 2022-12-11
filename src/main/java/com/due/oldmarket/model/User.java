package com.due.oldmarket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
@Data
@RequiredArgsConstructor
/*@NoArgsConstructor*/
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private String sex;
    private String birthday;
    @Value("active")
    private String status ;

    public User(String username, String password, String fullName, String email, String phoneNumber, String address, String sex, String birthday, String status) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.sex = sex;
        this.birthday = birthday;
        this.status = status;
    }

    public User(Long idUser, String username, String password, String fullName, String email, String phoneNumber, String address, String sex, String birthday, String status, Set<File> file, Set<Product> products, Set<Bill> bills, Set<Comment> comments, Set<Notification> notifications, Set<Role> roles, Set<Cart> carts) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.sex = sex;
        this.birthday = birthday;
        this.status = status;
        this.file = file;
        this.products = products;
        this.bills = bills;
        this.comments = comments;
        this.notifications = notifications;
        this.roles = roles;
        this.carts = carts;
    }
/*
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
*/

/*    public User(Long idUser, String username, String password, String fullName, String email, String phoneNumber, String address, String sex, Date birthday, Set<File> file, Set<Product> products, Set<Bill> bills, Set<Comment> comments, Set<Role> roles, Set<Cart> carts) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.sex = sex;
        this.birthday = birthday;
        this.file = file;
        this.products = products;
        this.bills = bills;
        this.comments = comments;
        this.roles = roles;
        this.carts = carts;
    }*/

    @OneToMany(mappedBy = "user")
    private Set<File> file;

/*    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }*/

    @OneToMany(mappedBy = "user")
    private Set<Product> products;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Bill> bills;

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "user")
    private Set<Notification> notifications;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role")
    )
    private Set<Role> roles;


    @OneToMany(mappedBy = "user")
    private Set<Cart> carts;

/*    public User() {
    }*/
/*

    public User(Long idUser, String username, String password, String fullName, String email, String phoneNumber, String address, Set<File> file) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.file = file;
    }
*/

/*    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }*/

/*    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Set<File> getFile() {
        return file;
    }

    public void setFile(Set<File> file) {
        this.file = file;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<Bill> getBills() {
        return bills;
    }

    public void setBills(Set<Bill> bills) {
        this.bills = bills;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Cart> getCarts() {
        return carts;
    }

    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }*/
}
