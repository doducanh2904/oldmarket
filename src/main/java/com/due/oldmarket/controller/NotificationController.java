package com.due.oldmarket.controller;

import com.due.oldmarket.dto.CommentDTO;
import com.due.oldmarket.model.Notification;
import com.due.oldmarket.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/notification")
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @GetMapping(value = "/findByIdProduct/{idProduct}")
    public ResponseEntity<List<Notification>> findByIdProduct (@PathVariable Long idProduct){
        List<Notification> notificationList = notificationService.findByIdProduct(idProduct);
        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }

    @GetMapping(value = "/findByIdUser/{idUser}")
    public ResponseEntity<List<Notification>> findByIdUser (@PathVariable Long idUser){
        List<Notification> notificationList = notificationService.findByIdUser(idUser);
        if(notificationList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }


}
