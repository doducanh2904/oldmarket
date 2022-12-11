package com.due.oldmarket.service;

import com.due.oldmarket.dto.BillReponse;
import com.due.oldmarket.dto.ProductDTO;
import com.due.oldmarket.dto.UserDTO;
import com.due.oldmarket.model.Bill;
import com.due.oldmarket.model.User;

import java.util.List;
import java.util.Set;

public interface BillService {
    String save(Bill bill, Long idUser, Long idProduct);

    /*Lấy ra những Bill chưa được xác nhận theo từng User người mua */
    List<Bill> findByIdUser(Long idUser, String status);

    /*Lấy ra những Bill chưa được xác nhận theo từng User người bán */
    List<Bill> findByIdUserSale(Long id, String status);

    String updateStatus(List<Long> idBillList, String status);

    Bill findByIdBill(Long idBill);

    List<Bill> findByIdProduct(Long idProduct, String status);

    UserDTO finByIdProductAndIdBil(Long idBill);

    ProductDTO finProductByIdBil(Long idBill);
}
