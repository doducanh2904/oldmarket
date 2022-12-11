package com.due.oldmarket.controller;

import com.due.oldmarket.dto.CommentDTO;
import com.due.oldmarket.dto.ProductDTO;
import com.due.oldmarket.exception.FileStorageException;
import com.due.oldmarket.model.Comment;
import com.due.oldmarket.model.File;
import com.due.oldmarket.model.Product;
import com.due.oldmarket.payload.UploadFileResponse;
import com.due.oldmarket.service.CommentService;
import com.due.oldmarket.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(value = "/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    FileStorageService fileStorageService;

    /**
     * Method này lấy ra  toàn bộ row Comment theo idProduct
     *
     * @return List<Comment>
     * @Param idProduct
     * @api : http://localhost:8080/comment/selectById/{id}
     * @method : GET
     */
    @GetMapping(value = "/selectById/{id}")
    public ResponseEntity<List<CommentDTO>> selectById(@PathVariable(value = "id") Long idProduct) {
        List<Comment> dataList = commentService.findAllById(idProduct);
        CommentDTO commentDTO;
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment data : dataList) {
            commentDTO =  new CommentDTO();
            commentDTO.setIdComment(data.getIdComment());
            commentDTO.setComment(data.getCommentContent());
            commentDTO.setIdUser(data.getUser().getIdUser());
            commentDTO.setIdProduct(data.getProduct().getIdProduct());
            List<String> idFileList = new ArrayList<>();
            for (File file : data.getFile()) {
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("file/downloadFile/")
                        .path(file.getId())
                        .toUriString();
                idFileList.add(fileDownloadUri);
            }
            commentDTO.setUrlFile(idFileList);

            commentDTOList.add(commentDTO);
        }
        if (commentDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    }


    /**
     * Method này insert 1 row comment và  1 file
     *
     * @param : comment
     * @param : idUer
     * @param : idProdduct
     * @param : MultipartFile file1
     * @api : http://localhost:8080/comment/insertCommentAndFile?idUser=...&idProduct=...
     * @method : POST
     */
    @PostMapping(value = "/insertCommentAndFile")
    public UploadFileResponse insertCommentAndFile(@ModelAttribute Comment comment, BindingResult result,
                                                   @RequestParam(name = "idUser") Long idUser,
                                                   @RequestParam(name = "idProduct") Long idProduct,
                                                   @RequestParam("file1") MultipartFile file1) {
        System.out.println("===== " + comment);
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (final FieldError error : result.getFieldErrors()) {
                System.out.println(errors.append("/" + error.getDefaultMessage()));
            }
        }
        try {
            System.out.println("111111111111");
            commentService.save(comment, idProduct, idUser);
            System.out.println("2222222222222");
        } catch (Exception e) {
            throw new FileStorageException("Đã xảy ra lỗi ", e);
        }
        if(file1.isEmpty()){
            return new UploadFileResponse("Add comment successfully and no image yet");
        }

        File dbFile = fileStorageService.storeFileComment(file1, comment.getIdComment());
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("file/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        return new UploadFileResponse(dbFile.getId(), dbFile.getFileName(), fileDownloadUri,
                file1.getContentType(), file1.getSize());

    }




    /**
     * Method này insert 1 row comment và nhiều file
     *
     * @param : comment
     * @param : idUer
     * @param : idProdduct
     * @param : MultipartFile[] files
     * @api : http://localhost:8080/comment/insertCommentAndMulFile?idUser=...&idProduct=...
     * @method : POST
     */
    @PostMapping(value = "/insertCommentAndMulFile")
    public List<UploadFileResponse> insertCommentAndMulFile(@ModelAttribute Comment comment, BindingResult result,
                                                            @RequestParam(name = "idUser") Long idUser,
                                                            @RequestParam(name = "idProduct") Long idProduct,
                                                            @RequestParam("files") MultipartFile[] files) {


        List<UploadFileResponse> repo = new ArrayList<UploadFileResponse>();

        for (MultipartFile file : files) {
            repo.add(insertCommentAndFile(comment, result, idUser, idProduct, file));
        }
        return repo;
    }

    /**
     * Method này xóa 1 row comment và file theo idComment
     *
     * @param : idComment
     * @api : http://localhost:8080/comment/delete/...
     * @method : DELETE
     */
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "id") Long id) {

        fileStorageService.deleteByIdComment(id);
        commentService.delete(id);

        return new ResponseEntity<>("Delete Comment Succesfully", HttpStatus.OK);
    }
}
