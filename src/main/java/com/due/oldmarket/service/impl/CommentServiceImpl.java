package com.due.oldmarket.service.impl;

import com.due.oldmarket.exception.NoSuchCommentExistsException;
import com.due.oldmarket.exception.NoSuchProductExistsException;
import com.due.oldmarket.model.Comment;
import com.due.oldmarket.model.Notification;
import com.due.oldmarket.model.Product;
import com.due.oldmarket.model.User;
import com.due.oldmarket.reponsitory.CommentReponsitory;
import com.due.oldmarket.reponsitory.ProductReponsitory;
import com.due.oldmarket.reponsitory.UserReponsitory;
import com.due.oldmarket.service.CommentService;
import com.due.oldmarket.service.NotificationService;
import com.due.oldmarket.service.ProductService;
import com.due.oldmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentReponsitory commentReponsitory;
    @Autowired
    UserReponsitory userReponsitory;
    @Autowired
    ProductReponsitory productReponsitory;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    NotificationService notificationService;

    public List<Comment> findAllById(Long idProduct) {
        /*Product product = productService.findById(idProduct);*/
        List<Comment> commentList = commentReponsitory.findAll();
        List<Comment> commentList1 = new ArrayList<>();
        for (Comment comment:commentList){
            if (comment.getProduct().getIdProduct()==idProduct)
            {
                commentList1.add(comment);
            }
        }
        return commentList1;

    }

    public String save(Comment comment, Long idProduct, Long idUser) {
       /* User user = userReponsitory.findById(idUser).orElse(null);*/
        User user = userService.findById(idUser);
        /*Product product = productReponsitory.findById(idProduct).orElse(null);*/
        Product product = productService.findById(idProduct);
        comment.setUser(user);
        comment.setProduct(product);
        commentReponsitory.save(comment);


        Notification notification = new Notification();
        notification.setProduct(product);
        notification.setStatus("1");
        notification.setMessage(user.getFullName() + " vừa bình luận vào bài đăng sản phẩm "+ product.getProductName() +" của bạn"  );
        notificationService.save(notification);


        return "comment successfully";
    }

    public String delete (Long idComment){
        commentReponsitory.deleteById(idComment);
        return "delete comment successfully";
    }
}
