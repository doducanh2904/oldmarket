package com.due.oldmarket.service;

import com.due.oldmarket.model.File;
import com.due.oldmarket.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Stream;

public interface FileStorageService {
    public File storeFile(MultipartFile file, Long userId);

    public File storeFileProduct(MultipartFile file, Long idProduct);

    public File storeFileComment(MultipartFile file, Long idComment);

    public File getFile(String fileId);

    public User findById(Long userId);

    public List<File> findByUserId(Long id);

    Stream<File> getAllFiles();

    Stream<File> getAllFilesById(Long idProduct);

    public File updateFile(MultipartFile file, String fileId);

    public List<File> updateMulFie(MultipartFile[] files, List<Long> idFile);

    String deleteByIdComment(Long id);


}
