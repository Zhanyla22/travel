package com.example.light_up_travel.repository;


import com.example.light_up_travel.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;



public interface LikesRepository extends JpaRepository<Likes,Long> {

    long countById(Long postId);

}
