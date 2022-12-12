package com.example.light_up_travel.repository;


import com.example.light_up_travel.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface LikesRepository extends JpaRepository<Likes,Long> {

    @Query(value = "SELECT count(user_id) FROM likes l where l.post_id =:postId",nativeQuery = true)
    long countById(Long postId);

    boolean existsByPost_IdAndUser_Id(Long postId, Long user_id);



}
