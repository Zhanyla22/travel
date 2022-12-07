package com.example.light_up_travel.repository;

import com.example.light_up_travel.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository  extends JpaRepository<Post,Long> {

}
