package com.due.oldmarket.controller;

import com.due.oldmarket.exception.MyFileNotFoundException;
import com.due.oldmarket.model.File;
import com.due.oldmarket.model.User;
import com.due.oldmarket.payload.UploadFileResponse;
import com.due.oldmarket.reponsitory.FileReponsitory;
import com.due.oldmarket.reponsitory.UserReponsitory;
import com.due.oldmarket.service.FileStorageService;
import com.due.oldmarket.service.impl.FileStorageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private FileReponsitory fileReponsitory;
    @Autowired
    private UserReponsitory userReponsitory;

    @PostMapping("/{userId}/file")
    public UploadFileResponse uploadFile(@PathVariable(value = "userId") Long userId, @RequestParam("file") MultipartFile file) {

        File dbFile = fileStorageService.storeFile(file, userId);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("file/downloadFile/")
                .path(dbFile.getId())
                .toUriString();
        return new UploadFileResponse(dbFile.getId(), dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles/{idUser}")
    public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @PathVariable(value = "idUser") Long id) {

        for (MultipartFile file : files) {
            uploadFile(id, file);
        }
        return "thanh cong";
    }


    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        File dbFile = fileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

    @GetMapping("/files")
    public ResponseEntity<List<UploadFileResponse>> getListFiles() {
        List<UploadFileResponse> files = fileStorageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/file/downloadFile/")
                    .path(dbFile.getId().toString())
                    .toUriString();

            return new UploadFileResponse(
                    dbFile.getId(),
                    dbFile.getFileName(),
                    fileDownloadUri,
                    dbFile.getFileType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

}
