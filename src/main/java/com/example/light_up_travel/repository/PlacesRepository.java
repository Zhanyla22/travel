package com.example.light_up_travel.repository;

import com.example.light_up_travel.entity.Place;
import com.example.light_up_travel.entity.Post;
import com.example.light_up_travel.enums.Status;
import com.example.light_up_travel.enums.Travel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlacesRepository extends JpaRepository<Place, Integer>{

    Page<Place> findByStatus(Status status, Pageable pageable);


}
