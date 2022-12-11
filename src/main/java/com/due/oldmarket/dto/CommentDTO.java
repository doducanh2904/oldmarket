package com.due.oldmarket.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class CommentDTO {
    private Long idComment;
    private String comment;
    private Long idUser;
    private Long idProduct;
    private List<String> urlFile;
}
