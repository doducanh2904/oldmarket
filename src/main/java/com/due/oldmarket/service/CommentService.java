package com.due.oldmarket.service;

import com.due.oldmarket.model.Comment;
import com.due.oldmarket.reponsitory.CommentReponsitory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface CommentService {
    List<Comment> findAllById(Long idProduct);
    String save (Comment comment,Long idProduct, Long idUser);
    String delete (Long idComment);
}
