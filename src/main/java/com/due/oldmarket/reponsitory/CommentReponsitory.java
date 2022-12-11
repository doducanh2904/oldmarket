package com.due.oldmarket.reponsitory;

import com.due.oldmarket.model.Comment;
import com.due.oldmarket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentReponsitory extends JpaRepository<Comment,Long> {
    @Query(value = "SELECT * FROM comment WHERE id_product LIKE %?1% ",nativeQuery = true)
    List<Comment> findAllByIdPrc(Long param);
}
