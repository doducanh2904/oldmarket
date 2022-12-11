package com.due.oldmarket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.Nullable;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comment")
    private Long idComment;
    private String commentContent;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false)
    @JsonIgnore
    private Product product;

    @OneToMany(mappedBy = "comment")
    private Set<File> file;

    public Set<File> getFile() {
        return file;
    }

    public void setFile(Set<File> file) {
        this.file = file;
    }

    public Comment() {
    }

    public Comment(Long idComment, String comment, User user, Product product) {
        this.idComment = idComment;
        this.commentContent = comment;
        this.user = user;
        this.product = product;
    }

    public Long getIdComment() {
        return idComment;
    }

    public void setIdComment(Long idComment) {
        this.idComment = idComment;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String comment) {
        this.commentContent = comment;
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
