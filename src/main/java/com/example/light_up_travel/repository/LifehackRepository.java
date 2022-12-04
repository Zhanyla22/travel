package com.example.light_up_travel.repository;

import com.example.light_up_travel.entity.Article;
import com.example.light_up_travel.entity.Lifehack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LifehackRepository extends JpaRepository<Lifehack, Long> {

    @Query(value = "SELECT * " +
            " FROM lifehack WHERE status = 'ACTIVE' ORDER BY date_created",nativeQuery = true)
    public List<Lifehack> getAllActiveLifehack();

    @Query(value = "SELECT * " +
            " FROM lifehack WHERE status = 'DELETED_BY_ADMIN' ORDER BY date_deleted",nativeQuery = true)
    public List<Lifehack> getAllDeletedLifehack();

}
