package com.example.light_up_travel.repository;

import com.example.light_up_travel.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumRepository extends JpaRepository<Form, Long> {
}
