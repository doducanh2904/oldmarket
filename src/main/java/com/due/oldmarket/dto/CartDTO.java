package com.due.oldmarket.dto;

import com.due.oldmarket.model.File;
import com.due.oldmarket.model.Product;
import com.due.oldmarket.model.User;
import com.due.oldmarket.payload.UploadFileResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@RequiredArgsConstructor
public class CartDTO {
    private Long idCart;
    private Long amount;
    private Long idUser;
    private Product product;
    private Set<File> file;
}
