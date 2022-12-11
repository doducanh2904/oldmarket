package com.due.oldmarket.reponsitory;

import com.due.oldmarket.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillReponsitory extends JpaRepository<Bill,Long> {

}
