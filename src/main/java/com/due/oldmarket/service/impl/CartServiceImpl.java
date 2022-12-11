package com.due.oldmarket.service.impl;

import com.due.oldmarket.dto.CartDTO;
import com.due.oldmarket.model.Cart;
import com.due.oldmarket.model.Product;
import com.due.oldmarket.model.User;
import com.due.oldmarket.reponsitory.CartReponsitory;
import com.due.oldmarket.reponsitory.ProductReponsitory;
import com.due.oldmarket.reponsitory.UserReponsitory;
import com.due.oldmarket.service.CartService;
import com.due.oldmarket.service.ProductService;
import com.due.oldmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartReponsitory cartReponsitory;
    @Autowired
    UserReponsitory userReponsitory;
    @Autowired
    ProductService productService;
    @Autowired
    ProductReponsitory productReponsitory;

    @Autowired
    UserService userService;

    public String save (Cart cart, Long idUser, Long idProduct){
        /*User user = userReponsitory.findById(idUser).orElse(null);*/
        User user = userService.findById(idUser);
        /*Product product = productReponsitory.findById(idProduct).orElse(null);*/
        Product product = productService.findById(idProduct);
        cart.setUser(user);
        cart.setProduct(product);
        cartReponsitory.save(cart);
        return "Save cart successfully";
    }

    @Override
    public List<Cart> findById(Long idUser) {
        /*User user = userReponsitory.findById(idUser).orElse(null);*/
        User user = userService.findById(idUser);
        Set<Cart> carts = user.getCarts();
        List<Cart>   cartList = new ArrayList<>();
        for (Cart a : carts){
            Cart cart = cartReponsitory.findById(a.getIdCart()).orElse(null);
            cartList.add(cart);
        }
        return cartList;
    }

    @Override
    public String delete(Long idCart) {
        Cart cart = cartReponsitory.findById(idCart).orElse(null);
        if (cart == null){
            return "Cart khong ton tai";
        }
        else {
            try {
                cartReponsitory.deleteById(idCart);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return "Delete Cart Successfully";
        }
    }

    @Override
    public Cart update(Long idCart, Cart cart) {
       Cart data = cartReponsitory.findById(idCart).orElse(null);
       data.setAmount(cart.getAmount());
        return cartReponsitory.save(data);

    }

    @Override
    public Cart findByIdCart(Long id) {
        return cartReponsitory.findById(id).orElse(null);
    }
}
