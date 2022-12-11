package com.due.oldmarket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.Nullable;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cart")
    private Long idCart;
    private Long amount;

    @ManyToOne
    @JoinColumn(name = "id_product", nullable = true)
    @JsonIgnore
    @Nullable
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = true)
    @JsonIgnore
    @Nullable
    private User user;

    public Cart() {
    }

    public Cart(Long idCart, Long amount, Product product, User user) {
        this.idCart = idCart;
        this.amount = amount;
        this.product = product;
        this.user = user;
    }

    public Long getIdCart() {
        return idCart;
    }

    public void setIdCart(Long idCart) {
        this.idCart = idCart;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
