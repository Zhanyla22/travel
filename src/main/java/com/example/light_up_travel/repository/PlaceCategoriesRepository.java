package com.example.light_up_travel.repository;

import com.example.light_up_travel.dto.GetPlaceWithCategDTO;
import com.example.light_up_travel.entity.PlaceCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaceCategoriesRepository extends JpaRepository<PlaceCategories, Long> {

    @Query(value="SELECT * from place_categories " +
            "JOIN places p " +
            "on" +
            " p.id = place_categories.place_id " +
            "JOIN category c" +
            " on " +
            "c.id = place_categories.category_id" +
            " WHERE category_id = :categoryId")
    List<PlaceCategories> getPlaceCategoriesByCategoryIsLike(int categoryId);

}
