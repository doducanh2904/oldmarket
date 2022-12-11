package com.due.oldmarket.service.impl;

import com.due.oldmarket.dto.ProductDTO;
import com.due.oldmarket.dto.UserDTO;
import com.due.oldmarket.model.*;
import com.due.oldmarket.reponsitory.BillReponsitory;
import com.due.oldmarket.reponsitory.ProductReponsitory;
import com.due.oldmarket.reponsitory.UserReponsitory;
import com.due.oldmarket.service.BillService;
import com.due.oldmarket.service.NotificationService;
import com.due.oldmarket.service.ProductService;
import com.due.oldmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    BillReponsitory billReponsitory;
    @Autowired
    UserReponsitory userReponsitory;
    @Autowired
    ProductReponsitory productReponsitory;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    NotificationService notificationService;

    @Override
    public String save(Bill bill, Long idUser, Long idProduct) {
        /*User user = userReponsitory.findById(idUser).orElse(null);*/
        User user = userService.findById(idUser);
        /*Product product = productReponsitory.findById(idProduct).orElse(null);*/
        Product product = productService.findById(idProduct);
        bill.setUser(user);
        bill.setProduct(product);
        billReponsitory.save(bill);

        Notification notification = new Notification();
        notification.setProduct(product);
        notification.setStatus("1");
        notification.setMessage(user.getFullName() + " vừa gửi yêu cầu mua sản phẩm "+ product.getProductName() +" của bạn"  );
        notificationService.save(notification);


        return "Save Bill successfully";
    }

    /*Lấy ra những Bill chưa được xác nhận theo từng User người mua */
    @Override
    public List<Bill> findByIdUser(Long idUser, String status) {
        /*User user = userReponsitory.findById(idUser).orElse(null);*/
        User user = userService.findById(idUser);
        List<Bill> billList = new ArrayList<>();
        Set<Bill> bills = user.getBills();
        for (Bill bill : bills) {
            if(bill.getStatus().equals(status)) {
                billList.add(bill);
            }
        }
        return billList;
    }

    /*Lấy ra những Bill chưa được xác nhận thanh toán theo từng id User người bán */
    @Override
    public List<Bill> findByIdUserSale(Long idUser, String status) {
        List<Bill> billList1 = new ArrayList<>();
        List<Bill> billList = billReponsitory.findAll();
        for (Bill bill : billList) {
            Product product = bill.getProduct();
            if (product.getUser().getIdUser() == idUser && bill.getStatus().equals(status)) {
                billList1.add(bill);
            }
        }
        return billList1;
    }

    @Override
    public String updateStatus(List<Long> idBillList, String status) {
        for (Long idBill : idBillList) {
            Bill bill = findByIdBill(idBill);
            bill.setStatus(status);
            billReponsitory.save(bill);
            if(status.equals("active")){
                Product product = bill.getProduct();
                product.setAmount(product.getAmount() - bill.getAmount());
                productReponsitory.save(product);
            }

            Notification notification = new Notification();
            notification.setUser(bill.getUser());
            notification.setStatus("1");
            notification.setMessage("Người bán vừa "+ status +" yêu cầu mua hàng của bạn của bạn"  );
            notificationService.save(notification);
        }



        return "update status list Bill successfully";
    }

    @Override
    public Bill findByIdBill(Long idBill) {
        Bill bill = billReponsitory.findById(idBill).orElse(null);
        return bill;
    }

    /*Method này lấy ra toàn bộ Bill bán hàng của 1 sản phẩm*/

    @Override
    public List<Bill> findByIdProduct(Long idProduct, String status) {
        Product product = productService.findById(idProduct);
        Set<Bill> billSet = product.getBills();
        List<Bill> billSet1 = new ArrayList<>();
        for (Bill bill : billSet) {
            if (bill.getStatus().equals(status)) {
                billSet1.add(bill);
            }
        }
        return billSet1;
    }


    /*Method lay ra User nguoi mua theo Bill va San Pham*/
    @Override
    public UserDTO finByIdProductAndIdBil(Long idBill) {

        Bill bill = billReponsitory.findById(idBill).orElse(null);
        UserDTO data = new UserDTO();

        data.setIdUser(bill.getUser().getIdUser());
        data.setAddress(bill.getUser().getAddress());
        data.setEmail(bill.getUser().getEmail());
        data.setFullName(bill.getUser().getFullName());
        data.setPassword(bill.getUser().getPassword());
        data.setPhoneNumber(bill.getUser().getPhoneNumber());
        data.setUsername(bill.getUser().getUsername());
        data.setBirthday(bill.getUser().getBirthday());
        data.setSex(bill.getUser().getSex());
        data.setStatus(bill.getUser().getStatus());

        return data;
    }

    @Override
    public ProductDTO finProductByIdBil(Long idBill) {

        Bill bill = billReponsitory.findById(idBill).orElse(null);
        ProductDTO productDTO = new ProductDTO();
        productDTO = new ProductDTO();
        productDTO.setIdProduct(bill.getProduct().getIdProduct());
        productDTO.setProductName(bill.getProduct().getProductName());
        productDTO.setPrice(bill.getProduct().getPrice());
        productDTO.setDescription(bill.getProduct().getDescription());
        productDTO.setTradePark(bill.getProduct().getTradePark());
        productDTO.setAmount(bill.getProduct().getAmount());
        productDTO.setIdUser(bill.getProduct().getUser().getIdUser());
        productDTO.setIdCategory(bill.getProduct().getCategory().getIdCategory());
        productDTO.setCategory(bill.getProduct().getCategory());

        List<String> idFileList = new ArrayList<>();
        for (File file : productService.findById(bill.getProduct().getIdProduct()).getFile()) {

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("file/downloadFile/")
                    .path(file.getId())
                    .toUriString();
            idFileList.add(fileDownloadUri);
        }
        productDTO.setUrlFile(idFileList);



        return productDTO;
    }

}
