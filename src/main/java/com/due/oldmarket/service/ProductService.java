package com.due.oldmarket.service;

import com.due.oldmarket.model.Product;
import com.due.oldmarket.model.ProductEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface ProductService {
    String save (Long iduser, Long idCategory, Product product);
    Product findById(Long id);
    List<Product> findAll();
    List<Product> findAll(int pageNo, int pageSize );

    String update(Long idProduct,Product product);
    String deteleList (List<Long> id,String status);

    List<Product> findByName (String param,int pageNo, int pageSize);

    List<Product> findByParamProduct(String param,int pageNo, int pageSize);

    List<Product> findByIdCategory (Long idCategory,int pageNo, int pageSize);
    Set<Product> findByIdUserSalesman (Long idUser, String status);
}
