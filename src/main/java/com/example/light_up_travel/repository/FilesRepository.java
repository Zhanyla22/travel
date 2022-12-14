package com.example.light_up_travel.repository;

import com.example.light_up_travel.entity.Files;
import com.example.light_up_travel.services.impl.LifehackService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FilesRepository extends JpaRepository<Files, Long> {

    List<Files> findAllByPlace_Id(Long id);

    @Query(value = "SELECT file_path FROM files where post =:postId",nativeQuery = true)
    List<Files> getAllByPost(Long postId);

}
