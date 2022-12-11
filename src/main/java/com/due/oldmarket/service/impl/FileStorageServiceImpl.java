package com.due.oldmarket.service.impl;

import com.due.oldmarket.exception.FileStorageException;
import com.due.oldmarket.exception.MyFileNotFoundException;
import com.due.oldmarket.model.Comment;
import com.due.oldmarket.model.File;
import com.due.oldmarket.model.Product;
import com.due.oldmarket.model.User;
import com.due.oldmarket.reponsitory.CommentReponsitory;
import com.due.oldmarket.reponsitory.FileReponsitory;
import com.due.oldmarket.reponsitory.ProductReponsitory;
import com.due.oldmarket.reponsitory.UserReponsitory;
import com.due.oldmarket.service.FileStorageService;
import com.due.oldmarket.service.ProductService;
import com.due.oldmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    @Autowired
    private FileReponsitory fileRepository;
    @Autowired
    private UserReponsitory userReponsitory;
    @Autowired
    private ProductReponsitory productReponsitory;
    @Autowired
    private CommentReponsitory commentReponsitory;
    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;


    public File storeFile(MultipartFile file, Long userId) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            /*User user = userReponsitory.findById(userId).orElse(null);*/
            User user = userService.findById(userId);
            File dbFile = new File(fileName, file.getContentType(), file.getBytes());
            dbFile.setUser(user);
            return fileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }


    public File storeFileProduct(MultipartFile file, Long idProduct) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
//            User user = userReponsitory.findById(userId).orElse(null);
//            Product product1 = productReponsitory.findById(idProduct).orElse(null);
            Product product1 = productService.findById(idProduct);
            File dbFile = new File(fileName, file.getContentType(), file.getBytes());
            dbFile.setProduct(product1);

            return fileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }


    public File storeFileComment(MultipartFile file, Long idComment) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
//            User user = userReponsitory.findById(userId).orElse(null);


            Comment comment1 = commentReponsitory.findById(idComment).orElse(null);
            System.out.println("3333333333333333  " + comment1.getIdComment());

            File dbFile = new File(fileName, file.getContentType(), file.getBytes());
            dbFile.setComment(comment1);
            return fileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public File updateFile(MultipartFile file, String fileId) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            File data = fileRepository.findById(fileId).orElse(null);
            data.setFileName(file.getName());
            data.setData(file.getBytes());
            data.setFileType(file.getContentType());
            return fileRepository.save(data);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public List<File> updateMulFie(MultipartFile[] files, List<Long> idFiles) {
        return null;
    }

    public String deleteByIdComment(Long id) {
        Comment comment = commentReponsitory.findById(id).orElse(null);
        Set<File> a = comment.getFile();
        for (File data : a) {
            fileRepository.deleteById(data.getId());
        }
        return "delete file with ID successfully";
    }

    public File getFile(String fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }


    @Override
    public User findById(Long id) {
        return userReponsitory.findById(id).orElse(null);
    }


    @Override
    public List<File> findByUserId(Long userId) {

        return fileRepository.findByUserId(userId);

    }

    @Override
    public Stream<File> getAllFiles() {
        return fileRepository.findAll().stream();
    }

    @Override
    public Stream<File> getAllFilesById(Long idProduct) {
        return null;
    }
}

