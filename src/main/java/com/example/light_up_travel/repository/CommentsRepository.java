package com.example.light_up_travel.repository;

import com.example.light_up_travel.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments,Long> {

    @Query("SELECT c FROM Comments c WHERE c.post.id = ?1 AND c.dateDeleted IS NULL")
    List<Comments> getAllNotDeletedCommentsByPostId(Long postId);

    @Query("SELECT c FROM Comments c WHERE c.user.id = ?1 AND c.dateDeleted IS NULL")
    List<Comments> getAllNotDeletedCommentsByUserId(Long userId);

    @Query("SELECT c FROM Comments c WHERE c.post.id = ?1 AND c.status = 3")
    List<Comments> getAllDeletedCommentsByUsersWithPostId(Long postId);

    @Query("SELECT c FROM Comments c WHERE c.post.id = ?1 AND c.status = 2")
    List<Comments> getAllDeletedCommentsByAdminWithPostId(Long userId);
}
