package com.due.oldmarket.reponsitory;

import com.due.oldmarket.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface FileReponsitory extends JpaRepository<File, String> {
    @Query(value = "select * from file where id_user =?1", nativeQuery = true)
    public List<File> findByUserId(Long id);

    @Query(value = "select * from file where id_product =?1", nativeQuery = true)
    public Stream<File> getAllFilesById(Long id);

}
