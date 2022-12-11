package com.due.oldmarket.dto;

import com.due.oldmarket.model.Product;
import com.due.oldmarket.model.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Column;
@Data
//@NoArgsConstructor
@RequiredArgsConstructor
public class BillReponse {
    private Long idBill;
    private String status;
    private Long amount;
    private Long totalPrice;
    private String createDate;
    private ProductDTO product;
    private UserDTO user;

}
