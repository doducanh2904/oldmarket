package com.due.oldmarket.reponsitory;

import com.due.oldmarket.model.ERole;
import com.due.oldmarket.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleReponsitory extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);
}
