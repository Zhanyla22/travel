package com.example.light_up_travel.repository;

import com.example.light_up_travel.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
