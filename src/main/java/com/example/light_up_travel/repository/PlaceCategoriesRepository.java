package com.example.light_up_travel.repository;

import com.example.light_up_travel.entity.PlaceCategories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceCategoriesRepository extends JpaRepository<PlaceCategories, Long> {
}
