package com.due.oldmarket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Long idProduct;
    private String productName;
    private Long price;
    private String description;
    private String tradePark;
    private String status;
    private Long amount;

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy = "product")
    private Set<File> file;

    @OneToMany(mappedBy = "product")
    private Set<Bill> bills;

    @OneToMany(mappedBy = "product")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "product")
    private Set<Cart> carts;

    @OneToMany(mappedBy = "product")
    private Set<Notification> notifications;

    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    @JsonIgnore
    private Category category;

    public Product() {
    }

    public Product(Long idProduct, String productName, Long price, String description, String tradePark,Long amount, String status, User user, Set<File> file, Set<Bill> bills, Set<Comment> comments, Set<Cart> carts, Category category) {
        this.idProduct = idProduct;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.tradePark = tradePark;
        this.amount= amount;
        this.status = status;
        this.user = user;
        this.file = file;
        this.bills = bills;
        this.comments = comments;
        this.carts = carts;
        this.category = category;

    }

    public Product(Long idProduct, String productName, Long price, String description, String tradePark, String status, Long amount, User user, Set<File> file, Set<Bill> bills, Set<Comment> comments, Set<Cart> carts, Set<Notification> notifications, Category category) {
        this.idProduct = idProduct;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.tradePark = tradePark;
        this.status = status;
        this.amount = amount;
        this.user = user;
        this.file = file;
        this.bills = bills;
        this.comments = comments;
        this.carts = carts;
        this.notifications = notifications;
        this.category = category;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTradePark() {
        return tradePark;
    }

    public void setTradePark(String tradePark) {
        this.tradePark = tradePark;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<File> getFile() {
        return file;
    }

    public void setFile(Set<File> file) {
        this.file = file;
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

    public Set<Cart> getCarts() {
        return carts;
    }

    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
