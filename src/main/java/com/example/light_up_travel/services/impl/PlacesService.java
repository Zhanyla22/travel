package com.example.light_up_travel.services.impl;

import com.example.light_up_travel.entity.*;

import com.example.light_up_travel.entity.Category;
import com.example.light_up_travel.entity.Files;
import com.example.light_up_travel.entity.Place;
import com.example.light_up_travel.entity.Rating;
import com.example.light_up_travel.enums.Status;
import com.example.light_up_travel.mapper.PlacesMapper;
import com.example.light_up_travel.dto.GetPlaceDTO;
import com.example.light_up_travel.dto.UserForPlaces;
import com.example.light_up_travel.repository.CategoryRepository;
import com.example.light_up_travel.repository.FilesRepository;
import com.example.light_up_travel.repository.PlacesRepository;
import com.example.light_up_travel.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlacesService {

    private final RatingRepository ratingRepository;

    private final PlacesRepository placesRepository;

    private final FileUploadService fileUploadService;

    private final FilesRepository filesRepository;

    private final CategoryRepository categoryRepository;

    public List<GetPlaceDTO> getAll(int page, int size,Long categoryId) throws Exception {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new Exception("category with  id = " + categoryId+ " not found")
        );
        Pageable pageable = PageRequest.of(page, size);
        Page<Place> places = placesRepository.findByStatus(Status.ACTIVE, pageable);
        List<GetPlaceDTO> result = new ArrayList<>();
        for (Place p : places.getContent()) {
            List<Rating> listOfRatings = ratingRepository.findAllByPlace_Id(p.getId());
            List<UserForPlaces> userForPlacesList = new ArrayList<>();
            for (Rating r : listOfRatings) {
                UserForPlaces userForPlaces = new UserForPlaces();
                userForPlaces.setName(r.getUser().getName());
                userForPlaces.setSurname(r.getUser().getSurname());
                userForPlaces.setProfileUrl(r.getUser().getProfileUrl());
                userForPlaces.setRatingId(r.getId());
                userForPlaces.setComment(r.getComment());
                userForPlaces.setRate(r.getRate());
                userForPlacesList.add(userForPlaces);
            }
            List<Files> filesList = filesRepository.findAllByPlace_Id(p.getId());
            List<String> list = new ArrayList<>();
            for (Files f : filesList) {
                list.add(f.getFilePath());
            }

            result.add(PlacesMapper.placeEntityToDTO(p, userForPlacesList, list, ratingRepository.avgRate(p.getId())));
        }
        return result;
    }

}
