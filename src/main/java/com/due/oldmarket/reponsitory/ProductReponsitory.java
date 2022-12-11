package com.due.oldmarket.reponsitory;

import com.due.oldmarket.model.File;
import com.due.oldmarket.model.Product;
import com.due.oldmarket.model.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReponsitory extends JpaRepository<Product, Long> {

    @Query(value = "select * from product where product_name like  %:param% ", nativeQuery = true)
    public Page<Product> findByNameProduct(String param, Pageable pageable);

    @Query(value = "SELECT * FROM PRODUCT  WHERE PRODUCT.trade_park LIKE %:param% OR PRODUCT.product_name LIKE %:param% ", nativeQuery = true)
    public Page<Product> findByParamProduct(String param, Pageable pageable);

    @Query(value = "SELECT * FROM PRODUCT  WHERE 1=1 AND PRODUCT.id_category LIKE %:idCategory% ", nativeQuery = true)
    public Page<Product> findByIdCategory(Long idCategory, Pageable pageable);

}
