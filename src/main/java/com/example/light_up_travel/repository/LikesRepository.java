package com.example.light_up_travel.repository;


import com.example.light_up_travel.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes,Long> {

    long countById(Long postId);
}
