package com.example.light_up_travel.repository;

import com.example.light_up_travel.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "select category from category where id=:parentId ",nativeQuery = true)
    String getParentName(int parentId);
}
