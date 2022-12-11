package com.due.oldmarket.reponsitory;

import com.due.oldmarket.dto.UserDTO;
import com.due.oldmarket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserReponsitory extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM user WHERE id_user LIKE %?1% OR email LIKE %?1% OR full_name LIKE %?1% OR phone_number LIKE %?1%",nativeQuery = true)
    List<User> findByParam(String param);

    @Query(value = "SELECT * FROM user WHERE email LIKE %?1% OR username LIKE %?1%",nativeQuery = true)
    User findByUsernamOrEmailAut(String param);

    Optional<User> findByEmail(String email);
    @Query(value = "SELECT * FROM user WHERE (email LIKE %?2% AND user.status not like 'deleted') OR (username LIKE %?1% AND user.status not like 'deleted')",nativeQuery = true)
    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}