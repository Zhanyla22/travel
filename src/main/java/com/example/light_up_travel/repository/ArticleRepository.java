package com.example.light_up_travel.repository;

import com.example.light_up_travel.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Long> {

    @Query(value = "SELECT * " +
            " FROM article WHERE status = 'ACTIVE' ORDER BY date_created",nativeQuery = true)
    public List<Article> getAllActiveArticles();

    @Query(value = "SELECT * " +
            " FROM article WHERE status = 'DELETED_BY_ADMIN' ORDER BY date_deleted",nativeQuery = true)
    public List<Article> getAllDeletedArticles();


}
