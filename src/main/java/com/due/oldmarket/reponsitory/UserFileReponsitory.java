package com.due.oldmarket.reponsitory;

import com.due.oldmarket.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFileReponsitory  extends JpaRepository<Product, Long> {


}