package com.due.oldmarket.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CommentReponse {
    private Long idComment;
    private String comment;
    private Long idUser;
    private Long idProduct;
    private String fileId;
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
    private String message;
    public CommentReponse(String message) {
        this.message = message;
    }
}
