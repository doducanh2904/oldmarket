package com.due.oldmarket.reponsitory;

import com.due.oldmarket.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartReponsitory extends JpaRepository<Cart,Long> {

}
