package com.example.light_up_travel.services.impl;

import com.example.light_up_travel.dto.CreatePlaceDto;
import com.example.light_up_travel.dto.GetPlaceWithCategDTO;

import com.example.light_up_travel.entity.*;
import com.example.light_up_travel.enums.Status;
import com.example.light_up_travel.mapper.GetPlaceWithCategoryMapper;
import com.example.light_up_travel.mapper.PlacesMapper;
import com.example.light_up_travel.dto.GetPlaceDTO;
import com.example.light_up_travel.dto.UserForPlaces;
import com.example.light_up_travel.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    private final PlaceCategoriesRepository placeCategoriesRepository;
    public List<GetPlaceDTO> getAll(int page, int size,Long categoryId) throws Exception {
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



     public List<GetPlaceWithCategDTO>  getAllPlacesByCategory(int categoryId){

         List<PlaceCategories> placeCategories = placeCategoriesRepository.getPlaceCategoriesByCategoryIsLike(categoryId);
           List<GetPlaceWithCategDTO> placeMapperList = new ArrayList<>();
           for(PlaceCategories p:placeCategories)
               placeMapperList.add(GetPlaceWithCategoryMapper.PlaceEntityToPlaceDTO(p,ratingRepository.avgRate(p.getId())));
               return placeMapperList;
    }

    public ResponseEntity<Long> createNewPlace(int categoryId, CreatePlaceDto createPlaceDto) {
        try {
            Category category = categoryRepository.getById(categoryId);
            PlaceCategories placeCategories = new PlaceCategories();
            Place place = new Place();
            place.setName(createPlaceDto.getName());
            place.setDescription(createPlaceDto.getDescription());
            place.setCity(createPlaceDto.getCity());
            place.setAddressLink(createPlaceDto.getAddressLink());
            place.setDateCreated(LocalDate.now());
            place.setStatus(Status.ACTIVE);
            placesRepository.saveAndFlush(place);

            placeCategories.setPlace(placesRepository.getById(place.getId()));
            placeCategories.setCategory(categoryRepository.getById(category.getId()));

            return new ResponseEntity<>(place.getId(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
