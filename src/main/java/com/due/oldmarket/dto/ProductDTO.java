package com.due.oldmarket.dto;

import com.due.oldmarket.model.Category;
import com.due.oldmarket.model.File;
import com.due.oldmarket.payload.UploadFileResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
//@NoArgsConstructor
@RequiredArgsConstructor
public class ProductDTO {
    private Long idProduct;
    private String productName;
    private Long price;
    private String description;
    private String tradePark;
    private Long amount;
    private Long idUser;
    private String status;
    private Long idCategory;
    private Category category;
    private List<String> urlFile;

//    public ProductDTO() {
//    }
//
//    public ProductDTO(Long idProduct, String productName, Long price, String description, String tradePark, Long idUser, Long idCategory) {
//        this.idProduct = idProduct;
//        this.productName = productName;
//        this.price = price;
//        this.description = description;
//        this.tradePark = tradePark;
//        this.idUser = idUser;
//        this.idCategory = idCategory;
//    }
//
//    public Long getIdProduct() {
//        return idProduct;
//    }
//
//    public void setIdProduct(Long idProduct) {
//        this.idProduct = idProduct;
//    }
//
//    public String getProductName() {
//        return productName;
//    }
//
//    public void setProductName(String productName) {
//        this.productName = productName;
//    }
//
//    public Long getPrice() {
//        return price;
//    }
//
//    public void setPrice(Long price) {
//        this.price = price;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getTradePark() {
//        return tradePark;
//    }
//
//    public void setTradePark(String tradePark) {
//        this.tradePark = tradePark;
//    }
//
//    public Long getIdUser() {
//        return idUser;
//    }
//
//    public void setIdUser(Long idUser) {
//        this.idUser = idUser;
//    }
//
//    public Long getIdCategory() {
//        return idCategory;
//    }
//
//    public void setIdCategory(Long idCategory) {
//        this.idCategory = idCategory;
//    }
}
