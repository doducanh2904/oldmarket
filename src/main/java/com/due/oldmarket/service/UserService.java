package com.due.oldmarket.service;

import com.due.oldmarket.dto.UserDTO;
import com.due.oldmarket.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    /*List<User> findById(Long iduser);*/
    List<User> findByParam (String param);
    void save(User user);
    void  deleteById(Long isUser);

    User updateById (Long idUser,User user);

    User findById(Long idUser);
    User findByUsernamOrEmailAut(String param);




}
