package com.due.oldmarket.controller;

import com.due.oldmarket.dto.UserDTO;
import com.due.oldmarket.exception.FileStorageException;
import com.due.oldmarket.model.Category;
import com.due.oldmarket.model.File;
import com.due.oldmarket.model.User;
import com.due.oldmarket.payload.UploadFileResponse;
import com.due.oldmarket.service.FileStorageService;
import com.due.oldmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Các Method liên quan đến Model User
 *
 * @author Do Duc Anh
 * @since 2022-10-23
 */

@CrossOrigin
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    UserService userService;


    /**
     * Method này lấy ra thông tin của toàn bộ user
     *
     * @return List<User> Thông tin toàn bộ USer
     * @api : http://localhost:8080/user/user-full
     * @method : GET
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/user-full")
    public ResponseEntity<List<UserDTO>> showAll() {
        List<User> userList = userService.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        UserDTO data;
        for (User user : userList) {

            data = new UserDTO();
            data.setIdUser(user.getIdUser());
            data.setAddress(user.getAddress());
            data.setEmail(user.getEmail());
            data.setFullName(user.getFullName());
            data.setPassword(user.getPassword());
            data.setPhoneNumber(user.getPhoneNumber());
            data.setUsername(user.getUsername());
            data.setBirthday(user.getBirthday());
            data.setSex(user.getSex());
            data.setStatus(user.getStatus());

            Set<String> urlImageSet = new HashSet<>();
            for (File file : user.getFile()) {
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("file/downloadFile/")
                        .path(file.getId())
                        .toUriString();
                urlImageSet.add(fileDownloadUri);
            }
            data.setUrlImageSet(urlImageSet);
            userDTOList.add(data);
        }

        if (userList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);

    }

    /**
     * Method này lấy ra thông tin của user theo điều kiện
     *
     * @param name
     * @return List<User> Thông tin user theo điều kiện từ client
     * @api : http://localhost:8080/user/userBy?param=...
     * @method : GET
     */

    @GetMapping(value = "/userBy")
    public ResponseEntity<List<UserDTO>> findByName(@RequestParam(name = "param") String name) {
        name = name.trim();
        List<User> userList = userService.findByParam(name);

        List<UserDTO> userDTOList = new ArrayList<>();
        UserDTO data;
        for (User user : userList) {

            data = new UserDTO();
            data.setIdUser(user.getIdUser());
            data.setAddress(user.getAddress());
            data.setEmail(user.getEmail());
            data.setFullName(user.getFullName());
            data.setPassword(user.getPassword());
            data.setPhoneNumber(user.getPhoneNumber());
            data.setUsername(user.getUsername());
            data.setBirthday(user.getBirthday());
            data.setSex(user.getSex());
            data.setStatus(user.getStatus());
            Set<String> urlImageSet = new HashSet<>();
            for (File file : user.getFile()) {
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("file/downloadFile/")
                        .path(file.getId())
                        .toUriString();
                urlImageSet.add(fileDownloadUri);
            }
            data.setUrlImageSet(urlImageSet);

            userDTOList.add(data);


        }
        if (userList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }


    /**
     * Method này insert thêm 1 user
     *
     * @param : Model User
     * @param : result
     * @return HttpStatus
     * @api : http://localhost:8080/user/insert
     * @method : POST
     */

    @PostMapping(value = "/insert")
    public ResponseEntity<User> insert(@RequestBody User user, BindingResult result) {

        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (final FieldError error : result.getFieldErrors()) {
                System.out.println(errors.append("/" + error.getDefaultMessage()));
            }
        }
        user.setStatus("active");
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PostMapping("/{userId}/file")
//    public UploadFileResponse uploadFile(@PathVariable(value = "userId") Long userId, @RequestParam("file") MultipartFile file) {
//
//        File dbFile = fileStorageService.storeFile(file, userId);
//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/downloadFile/")
//                .path(dbFile.getId())
//                .toUriString();
//        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
//                file.getContentType(), file.getSize());
//    }

    /**
     * Method này insert thêm 1 user và 1 file
     *
     * @param : Model User
     * @param : result
     * @param : MultipartFile file
     * @return : UploadFileResponse
     * @api : http://localhost:8080/user/insertUserFile
     * @method : POST
     */
    @PostMapping(value = "/insertUserFile")
    public UploadFileResponse insertUserFIle(@ModelAttribute User user, BindingResult result,
                                             @RequestParam("file") MultipartFile file) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (final FieldError error : result.getFieldErrors()) {
                System.out.println(errors.append("/" + error.getDefaultMessage()));
            }
        }
        try {
            user.setStatus("active");
            userService.save(user);
        } catch (Exception e) {
            throw new FileStorageException("Đã xảy ra lỗi ", e);
        }
        if (file.isEmpty()) {
            return new UploadFileResponse("User registered successfully and no avatar yet");
        }

        File dbFile = fileStorageService.storeFile(file, user.getIdUser());
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        return new UploadFileResponse(dbFile.getId(), dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());

    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<User> deleteById(@PathVariable(value = "id") Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Method này chỉnh sửa  1 user theo id
     *
     * @param : Model User
     * @param : id
     * @return : User
     * @api : http://localhost:8080/user/update/{id}
     * @method : PUT
     */

    @PutMapping(value = "/update/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserDTO> update(@RequestBody User user, @PathVariable(value = "id") Long id) {
        userService.updateById(id, user);
        User user1 = userService.findById(id);
        UserDTO data = new UserDTO();
        data.setIdUser(user1.getIdUser());
        data.setAddress(user1.getAddress());
        data.setEmail(user1.getEmail());
        data.setFullName(user1.getFullName());
        data.setPassword(user1.getPassword());
        data.setPhoneNumber(user1.getPhoneNumber());
        data.setUsername(user1.getUsername());
        data.setBirthday(user1.getBirthday());
        data.setSex(user1.getSex());
        data.setStatus(user1.getStatus());

        Set<String> urlImageSet = new HashSet<>();
        for (File file : user1.getFile()) {
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("file/downloadFile/")
                    .path(file.getId())
                    .toUriString();
            urlImageSet.add(fileDownloadUri);
        }
        data.setUrlImageSet(urlImageSet);


        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PutMapping(value = "/updateUserFile/{idUser}/{idFile}")
    public ResponseEntity<UserDTO> updateUserFIle(@ModelAttribute User user,
                                                  BindingResult result,
                                                  @RequestParam("file") MultipartFile file,
                                                  @PathVariable(value = "idUser") Long idUser,
                                                  @PathVariable(value = "idFile") String idFile) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (final FieldError error : result.getFieldErrors()) {
                System.out.println(errors.append("/" + error.getDefaultMessage()));
            }
        }
        try {
            userService.updateById(idUser, user);



        } catch (Exception e) {
            throw new FileStorageException("Đã xảy ra lỗi ", e);
        }

        File dbFile = fileStorageService.updateFile(file, idFile);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("file/downloadFile/")
                .path(dbFile.getId())
                .toUriString();
        Set<String> setUrlImage = new HashSet<>();
        setUrlImage.add(fileDownloadUri);
        User user1 = userService.findById(idUser);

        UserDTO data = new UserDTO();
        data.setIdUser(user1.getIdUser());
        data.setAddress(user1.getAddress());
        data.setEmail(user1.getEmail());
        data.setFullName(user1.getFullName());
        data.setPassword(user1.getPassword());
        data.setPhoneNumber(user1.getPhoneNumber());
        data.setUsername(user1.getUsername());
        data.setBirthday(user1.getBirthday());
        data.setSex(user1.getSex());
        data.setStatus(user1.getStatus());
        data.setUrlImageSet(setUrlImage);

        /*return new UploadFileResponse(dbFile.getId(), dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());*/

        return new ResponseEntity<>(data,HttpStatus.OK);

    }


    /**
     * Method này tìm kiếm User theo ID
     *
     * @param : idUser
     * @return : User
     * @api : http://localhost:8080/user/selectById/{id}
     * @method : GET
     */

    @GetMapping(value = "/selectById/{id}")
    public ResponseEntity<UserDTO> selectById(@PathVariable(value = "id") Long id) {
        User user = userService.findById(id);
        UserDTO data = new UserDTO();
        data.setIdUser(user.getIdUser());
        data.setAddress(user.getAddress());
        data.setEmail(user.getEmail());
        data.setFullName(user.getFullName());
        data.setPassword(user.getPassword());
        data.setPhoneNumber(user.getPhoneNumber());
        data.setUsername(user.getUsername());
        data.setBirthday(user.getBirthday());
        data.setSex(user.getSex());
        data.setStatus(user.getStatus());

        Set<String> urlImageSet = new HashSet<>();
        for (File file : user.getFile()) {
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("file/downloadFile/")
                    .path(file.getId())
                    .toUriString();
            urlImageSet.add(fileDownloadUri);
        }
        data.setUrlImageSet(urlImageSet);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

}