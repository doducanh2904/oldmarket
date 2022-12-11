package com.due.oldmarket.controller;

import com.due.oldmarket.dto.CartDTO;
import com.due.oldmarket.model.Cart;
import com.due.oldmarket.model.User;
import com.due.oldmarket.service.CartService;
import com.due.oldmarket.service.ProductService;
import com.due.oldmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;


    /**
     * Method này insert 1 row CART
     *
     * @param : cart
     * @param : idUer
     * @param : idProdduct
     * @api : http://localhost:8080/cart/insert?idUser=...&idProduct=...
     * @method : POST
     */

    @PostMapping(value = "/insert")
    public ResponseEntity<String> insert(@RequestBody Cart cart,
                                         BindingResult result,
                                         @RequestParam(value = "idUser") Long idUSer,
                                         @RequestParam(value = "idProduct") Long idProduct) {


        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (final FieldError error : result.getFieldErrors()) {
                System.out.println(errors.append("/" + error.getDefaultMessage()));
            }
        }
        cartService.save(cart, idUSer, idProduct);
        System.out.println("===== "+ cart.getAmount());
        return new ResponseEntity<>("Save successflly", HttpStatus.OK);
    }


    /**
     * Method này lấy ra row cart theo ID USer người mua
     *
     * @param : idUer
     * @api : http://localhost:8080/cart/findById/...
     * @method : GET
     */

    @GetMapping(value = "/findById/{idUser}")
    public ResponseEntity<List<CartDTO>> findById(@PathVariable(value = "idUser") Long idUser) {
        List<Cart> cartList = cartService.findById(idUser);
        List<CartDTO> cartDTOList = new ArrayList<>();
        CartDTO cartDTO;
        for (Cart cart : cartList) {
            cartDTO = new CartDTO();
            cartDTO.setIdCart(cart.getIdCart());
            cartDTO.setAmount(cart.getAmount());
            cartDTO.setIdUser(cart.getUser().getIdUser());
            cartDTO.setProduct(cart.getProduct());
            cartDTO.setFile(cart.getProduct().getFile());
            cartDTOList.add(cartDTO);
        }

        return new ResponseEntity<>(cartDTOList, HttpStatus.OK);
    }


    /**
     * Method này xóa row cart theo ID Cart truyền vào
     *
     * @param : ID Cart
     * @api : http://localhost:8080/cart/delete/...
     * @method : GET
     */

    @DeleteMapping(value = "/delete/{idCart}")
    public ResponseEntity<String> deleteById(@PathVariable(value = "idCart") Long id) {
        cartService.delete(id);
        return new ResponseEntity<>("Delete Successfully", HttpStatus.OK);
    }

    /**
     * Method này sửa thông tin  row cart theo ID Cart truyền vào
     *
     * @param : ID Cart
     * @api : http://localhost:8080/cart/update/...
     * @method : GET
     */
    @PutMapping(value = "/update/{idCart}")
    public ResponseEntity<Cart> updateById(@ModelAttribute Cart cart,
                                           @PathVariable(value = "idCart") Long id) {
        cartService.update(id, cart);
        return new ResponseEntity<>(cartService.findByIdCart(id), HttpStatus.OK);
    }
}
