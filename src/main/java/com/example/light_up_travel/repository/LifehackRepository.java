package com.example.light_up_travel.repository;

import com.example.light_up_travel.entity.Article;
import com.example.light_up_travel.entity.Lifehack;
import com.example.light_up_travel.entity.Post;
import com.example.light_up_travel.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LifehackRepository extends JpaRepository<Lifehack, Long> {

    @Query(value = "SELECT * " +
            " FROM lifehack WHERE status = 'ACTIVE' ORDER BY date_created",nativeQuery = true)
    List<Lifehack> getAllActiveLifehack();

    @Query(value = "SELECT * " +
            " FROM lifehack WHERE status = 'DELETED_BY_ADMIN' ORDER BY date_deleted",nativeQuery = true)
    List<Lifehack> getAllDeletedLifehack();

    Page<Lifehack> findByStatus(Status status, Pageable pageable);

}
