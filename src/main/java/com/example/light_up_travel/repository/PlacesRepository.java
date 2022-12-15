package com.example.light_up_travel.repository;

import com.example.light_up_travel.dto.GetPlaceByNameDTO;
import com.example.light_up_travel.dto.GetPlaceWithCategDTO;
import com.example.light_up_travel.entity.Place;
import com.example.light_up_travel.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PlacesRepository extends JpaRepository<Place, Long>{

    Page<Place> findByStatus(Status status, Pageable pageable);

    @Query(value = "SELECT * FROM places WHERE name = :name",nativeQuery = true)
    Place getPlaceByName(String name);

}
