package com.due.oldmarket.service.impl;

import com.due.oldmarket.dto.UserDTO;
import com.due.oldmarket.exception.NoSuchProductExistsException;
import com.due.oldmarket.exception.NoSuchUserExistsException;
import com.due.oldmarket.model.User;
import com.due.oldmarket.reponsitory.UserReponsitory;
import com.due.oldmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserReponsitory userReponsitory;

    public List<User> findAll() {
        return userReponsitory.findAll();
    }

    public List<User> findByParam(String param) {
        return userReponsitory.findByParam(param);
    }

    public void save(User user) {
        userReponsitory.save(user);
    }

    public void deleteById(Long idUser) {
        User user = findById(idUser);
        user.setStatus("deleted");
        userReponsitory.save(user);
    }

    public User updateById(Long idUser, User user) {
        User data = userReponsitory.findById(idUser).orElse(null);
        data.setAddress(user.getAddress());
        data.setBirthday(user.getBirthday());
        data.setEmail(user.getEmail());
        data.setFullName(user.getFullName());
        data.setPassword(encoder.encode(user.getPassword()));
        data.setSex(user.getSex());
        data.setUsername(user.getUsername());
        data.setPhoneNumber(user.getPhoneNumber());
        userReponsitory.save(data);
        return data;
    }

    public User findById(Long idUser) {
        return userReponsitory.findById(idUser).orElseThrow(() -> new NoSuchUserExistsException(
                "No User present with ID = " + idUser));
    }

    public User findByUsernamOrEmailAut(String param) {
        return userReponsitory.findByUsernamOrEmailAut(param);
    }
}
