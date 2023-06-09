package com.example.light_up_travel.repository;

import com.example.light_up_travel.entity.Favourite;
import com.example.light_up_travel.entity.Forum;
import com.example.light_up_travel.entity.Post;
import com.example.light_up_travel.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostRepository  extends JpaRepository<Post,Long> {

    Page<Post> findByStatus(Status status, Pageable pageable);

    @Query(value = "SELECT * from post  where user_id = :userId AND status='ACTIVE'",nativeQuery = true)
    List<Post> findAllPostsByUser(Long userId);

    @Query(value = "SELECT * FROM post where status='WAITING_FOR_APPROVE'",nativeQuery = true)
    List<Post> getAllPostPending();

}
