package com.due.oldmarket.controller;

import com.due.oldmarket.dto.BillReponse;
import com.due.oldmarket.dto.UserDTO;
import com.due.oldmarket.model.Bill;
import com.due.oldmarket.model.Cart;
import com.due.oldmarket.model.User;
import com.due.oldmarket.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping(value = "/bill")
public class BillController {
    @Autowired
    BillService billService;

    /**
     * Method này insert thông tin vào bảng Bill
     *
     * @Param idUser
     * @Param idProduct
     * @api : http://localhost:8080/bill/save?idUser=...&idProduct=...
     * @method : POST
     */

    @PostMapping(value = "/save")
    public ResponseEntity<String> save(@RequestBody Bill bill,
                                       BindingResult result,
                                       @RequestParam(value = "idUser") Long idUser,
                                       @RequestParam(value = "idProduct") Long idProduct) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (final FieldError error : result.getFieldErrors()) {
                System.out.println(errors.append("/" + error.getDefaultMessage()));
            }
        }
        bill.setStatus("pending");
        billService.save(bill, idUser, idProduct);

        return new ResponseEntity<>("Save successflly", HttpStatus.OK);
    }

    /**
     * Method này lấy ra  thông tin bảng Bill theo ID USer người mua
     *
     * @Param idUser
     * @api : http://localhost:8080/bill/select/...
     * @method : GET
     */

    @GetMapping(value = "/select/{idUser}")
    public ResponseEntity<List<BillReponse>> findBiIdUser(@PathVariable(value = "idUser") Long idUser,
                                                          @RequestParam(value = "status") String status) {
        List<Bill> billList = billService.findByIdUser(idUser, status);
        List<BillReponse> billReponseList = new ArrayList<>();
        BillReponse billReponse;
        for (Bill bill : billList) {
            billReponse = new BillReponse();
            billReponse.setIdBill(bill.getIdBill());
            billReponse.setAmount(bill.getAmount());
            billReponse.setCreateDate(bill.getCreateDate());
            billReponse.setStatus(bill.getStatus());
            billReponse.setTotalPrice(bill.getTotalPrice());
            billReponse.setProduct(billService.finProductByIdBil(bill.getIdBill()));

            billReponseList.add(billReponse);

        }
        return new ResponseEntity<>(billReponseList, HttpStatus.OK);
    }


    /**
     * Method này lấy ra  thông tin bảng Bill theo ID USer người bán
     *
     * @Status : pending hoặc accept hoặc deleted
     * @Param idUser
     * @api : http://localhost:8080/bill/selectByUser/...
     * @method : GET
     */
    @GetMapping(value = "/selectByUser/{idUser}")
    public ResponseEntity<List<BillReponse>> selectByUser(@PathVariable(value = "idUser") Long id,
                                                          @RequestParam(value = "status") String status) {
        List<Bill> billList = billService.findByIdUserSale(id, status);
        List<BillReponse> billReponseList = new ArrayList<>();
        BillReponse billReponse;
        for (Bill bill : billList) {
            billReponse = new BillReponse();
            billReponse.setIdBill(bill.getIdBill());
            billReponse.setStatus(bill.getStatus());
            billReponse.setTotalPrice(bill.getTotalPrice());
            billReponse.setAmount(bill.getAmount());
            billReponse.setCreateDate(bill.getCreateDate());
            billReponse.setUser(billService.finByIdProductAndIdBil(bill.getIdBill()));
            billReponse.setProduct(billService.finProductByIdBil(bill.getIdBill()));

            billReponseList.add(billReponse);
        }

        return new ResponseEntity<>(billReponseList, HttpStatus.OK);
    }

    /**
     * Method này chỉnh sửa thông tin Status xác nhận đơn hàng (Role User người bán) trong bảng Bill theo ID Bill
     *
     * @Param idBill
     * @api : http://localhost:8080/bill/updateStatus/{idBill}?active or deleted
     * @method : PUT
     */

    @PutMapping(value = "/updateStatus/{idBill}")
    public ResponseEntity<String> updateStatus(@PathVariable(value = "idBill") List<Long> idBillList,
                                               @RequestParam(value = "status") String status) {
        billService.updateStatus(idBillList, status);
        if (status.equals("active")) {
            return new ResponseEntity<>("accept Bill successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("deleted Bill successfully", HttpStatus.OK);
    }


    /**
     * Method này lấy ra  thông tin bảng Bill theo ID sản phẩm và status
     * status = pending ==> những Bill đang chờ
     * status = active ==> những Bill đã chấp nhận bán
     * status = pending ==> những Bill đã từ chối bán
     *
     * @Param idUser
     * @api : http://localhost:8080/bill/findByIdProduct/{idProduct}?status=...
     * @method : GET
     */
    @GetMapping(value = "/findByIdProduct/{idProduct}")
    public ResponseEntity<List<BillReponse>> findByIdProduct(@PathVariable(value = "idProduct") Long idProduct,
                                                             @RequestParam(value = "status") String status) {
        List<Bill> billSet = billService.findByIdProduct(idProduct, status);
        List<BillReponse> billReponseList = new ArrayList<>();
        BillReponse billReponse;
        for (Bill bill : billSet) {
            billReponse = new BillReponse();
            billReponse.setIdBill(bill.getIdBill());
            billReponse.setStatus(bill.getStatus());
            billReponse.setTotalPrice(bill.getTotalPrice());
            billReponse.setAmount(bill.getAmount());
            billReponse.setCreateDate(bill.getCreateDate());
            billReponse.setUser(billService.finByIdProductAndIdBil(bill.getIdBill()));

            billReponseList.add(billReponse);
        }

        return new ResponseEntity<>(billReponseList, HttpStatus.OK);
    }

}
