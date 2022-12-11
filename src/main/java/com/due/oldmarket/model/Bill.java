package com.due.oldmarket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ib_bill")
    private Long idBill;
    @Column(name = "status")
    @Value("100")
    private String status;
    private Long amount;
    private Long totalPrice;
    private String createDate;

    @ManyToOne
    @JoinColumn(name = "id_user" , nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "bill")
    private Set<Notification> notifications;

    @ManyToOne
    @JoinColumn(name = "id_product" , nullable = false)
    @JsonIgnore
    private Product product;

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    public Bill(Long idBill, String status, Long amount, Long totalPrice, String createDate, User user, Set<Notification> notifications, Product product) {
        this.idBill = idBill;
        this.status = status;
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.createDate = createDate;
        this.user = user;
        this.notifications = notifications;
        this.product = product;
    }

    public Bill(Long idBill, String status, Long amount, Long totalPrice, String createDate, User user, Product product) {
        this.idBill = idBill;
        this.status = status;
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.createDate = createDate;
        this.user = user;
        this.product = product;
    }

    public Bill() {
    }

    public Long getIdBill() {
        return idBill;
    }

    public void setIdBill(Long idBill) {
        this.idBill = idBill;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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
}
