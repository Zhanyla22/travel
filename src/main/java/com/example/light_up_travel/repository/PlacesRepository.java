package com.example.light_up_travel.repository;

import com.example.light_up_travel.entity.Place;
import com.example.light_up_travel.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlacesRepository extends JpaRepository<Place, Integer>{

    Page<Place> findByStatus(Status status, Pageable pageable);



}
