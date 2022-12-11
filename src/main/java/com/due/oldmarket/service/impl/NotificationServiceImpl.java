package com.due.oldmarket.service.impl;

import com.due.oldmarket.model.Comment;
import com.due.oldmarket.model.Notification;
import com.due.oldmarket.reponsitory.NotificationReponsitory;
import com.due.oldmarket.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    NotificationReponsitory notificationReponsitory;

    @Override
    public void save(Notification notification) {
        notificationReponsitory.save(notification);
    }

    @Override
    public List<Notification> findByIdUser(Long idUser) {
        List<Notification> notificationList = notificationReponsitory.findAll();
        List<Notification> notificationList1 = new ArrayList<>();
        for (Notification notification : notificationList) {
            if (notification.getUser()!= null && notification.getUser().getIdUser() == idUser &&  notification.getStatus().equals("1")) {
                notificationList1.add(notification);

                //sau khi select ra xem thì chuyển status về 0 tức là đã xem
                notification.setStatus("0");
                notificationReponsitory.save(notification);
            }

        }
        return notificationList1;
    }


    @Override
    public List<Notification> findByIdProduct(Long idProduct) {
        List<Notification> notificationList = notificationReponsitory.findAll();
        List<Notification> notificationList1 = new ArrayList<>();
        for (Notification notification : notificationList) {
            if (notification.getProduct()!= null && notification.getProduct().getIdProduct() == idProduct && notification.getStatus().equals("1") ) {
                notificationList1.add(notification);

                //sau khi select ra xem thì chuyển status về 0 tức là đã xem
                notification.setStatus("0");
                notificationReponsitory.save(notification);
            }
        }
        return notificationList1;
    }
}
