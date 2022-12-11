package com.example.light_up_travel.repository;

import com.example.light_up_travel.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating,Long> {
    @Query(value ="SELECT AVG(r.rate) FROM rating r WHERE r.place = ?1",nativeQuery = true)
    double avgRate(Long placeId);
    List<Rating> findAllByPlace_Id(Long placeId);
}
