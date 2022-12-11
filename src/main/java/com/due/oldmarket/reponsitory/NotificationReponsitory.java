package com.due.oldmarket.reponsitory;

import com.due.oldmarket.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationReponsitory extends JpaRepository<Notification, Long> {

}
