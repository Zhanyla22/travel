package com.example.light_up_travel.repository;

import com.example.light_up_travel.entity.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long> {

    @Query("SELECT m FROM Mentor m WHERE m.menteeId = ?1")
    Optional<Mentor> findByMentorId(Long id);

}
