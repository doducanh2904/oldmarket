package com.due.oldmarket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notification")
    private Long id;
    private String message;
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = true)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_product", nullable = true)
    @JsonIgnore
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_bill", nullable = true)
    @JsonIgnore
    private Bill bill;

    public Notification(Long id, String message, String status, User user, Product product, Bill bill) {
        this.id = id;
        this.message = message;
        this.status = status;
        this.user = user;
        this.product = product;
        this.bill = bill;
    }

    public Notification() {

    }

    public Notification(String message) {

        this.message = message;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
