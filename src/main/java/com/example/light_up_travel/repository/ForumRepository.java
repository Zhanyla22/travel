package com.example.light_up_travel.repository;

import com.example.light_up_travel.entity.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {

    @Query("SELECT f FROM Forum f WHERE f.dateDeleted is null and f.status = 'APPROVED'")
    List<Forum> findAllNotDeletedForums();

    @Query("SELECT f FROM Forum f WHERE f.dateDeleted is not null and f.status = 'DISAPPROVED'")
    List<Forum> findAllDeletedForums();

    @Query("SELECT f FROM Forum f WHERE f.id = ?1 and f.dateDeleted is not null")
    Optional<Forum> findDeletedUserById(Long id);

    @Query(value = "SELECT * FROM Forum where status='PENDING'",nativeQuery = true)
    List<Forum> getAllForumPending();
}
