package com.example.light_up_travel.services;

import com.example.light_up_travel.dto.*;

import com.example.light_up_travel.entity.*;
import com.example.light_up_travel.enums.Status;
import com.example.light_up_travel.exceptions.NotFoundException;
import com.example.light_up_travel.exceptions.NotFoundResourceException;
import com.example.light_up_travel.mapper.GetPlaceWithCategoryMapper;
import com.example.light_up_travel.mapper.PlacesMapper;
import com.example.light_up_travel.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private final UserService userService;

    private final UserRepository userRepository;

    public ResponseEntity<GetPlaceDTO> getActivePlaceById(Long placeId) throws Exception {
        try {
            Place place = placesRepository.findById(placeId).orElseThrow(
                    () -> new NotFoundException("Place with id" + placeId + "not found")
            );
            List<Rating> listOfRatings = ratingRepository.findAllByPlace_Id(place.getId());
            List<UserForPlacesDTO> userForPlacesDTOList = new ArrayList<>();
            for (Rating r : listOfRatings) {
                UserForPlacesDTO userForPlacesDTO = new UserForPlacesDTO();
                userForPlacesDTO.setName(r.getUser().getName());
                userForPlacesDTO.setSurname(r.getUser().getSurname());
                userForPlacesDTO.setProfileUrl(r.getUser().getProfileUrl());
                userForPlacesDTO.setRatingId(r.getId());
                userForPlacesDTO.setComment(r.getComment());
                userForPlacesDTO.setRate(r.getRate());
                userForPlacesDTOList.add(userForPlacesDTO);
            }
            List<Files> filesList = filesRepository.findAllByPlace_Id(place.getId());
            List<String> list = new ArrayList<>();
            for (Files f : filesList) {
                list.add(f.getFilePath());
            }

            GetPlaceDTO getPlaceDTO = PlacesMapper.placeEntityToDTO(place, userForPlacesDTOList, list, ratingRepository.avgRate(place.getId()));
            return new ResponseEntity<>(getPlaceDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    public List<GetPlaceWithCategDTO> getAllPlacesByCategory(int categoryId) {

        List<PlaceCategories> placeCategories = placeCategoriesRepository.getPlaceCategoriesByCategoryIsLike(categoryId);
        List<GetPlaceWithCategDTO> placeMapperList = new ArrayList<>();
        for (PlaceCategories p : placeCategories)
            placeMapperList.add(GetPlaceWithCategoryMapper.PlaceEntityToPlaceDTO(p, ratingRepository.avgRate(p.getId())));
        return placeMapperList;
    }

    public ResponseEntity<GetPlaceByNameDTO> getPlaceByName(String placeName) {
        Place place = placesRepository.getPlaceByName(placeName);
        GetPlaceByNameDTO getPlaceByNameDTO = new GetPlaceByNameDTO();
        getPlaceByNameDTO.setName(place.getName());
        getPlaceByNameDTO.setRate(ratingRepository.avgRate(place.getId()));
        getPlaceByNameDTO.setMainFilePath(place.getMainFilePath());

        return new ResponseEntity<>(getPlaceByNameDTO, HttpStatus.OK);

    }

    public ResponseEntity<Long> createNewPlace(int categoryId, CreatePlaceDTO createPlaceDto) {
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

            placeCategoriesRepository.save(placeCategories);

            return new ResponseEntity<>(place.getId(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @SneakyThrows
    public String saveMainImageForPlace(Long placeId, MultipartFile multipartFile) throws IOException {
        Place place = placesRepository.findById(placeId)
                .orElseThrow(
                        () -> new NotFoundResourceException("Place was not found with id: " + placeId)
                );

        place.setMainFilePath(fileUploadService.saveFile(multipartFile));

        placesRepository.save(place);

        return "Saved main image for place with id = " + placeId;
    }

    public ResponseEntity<Long> updatePlace(UpdatePlaceDTO updatePlaceDTO) throws Exception {
        try {
            Place place = placesRepository.findById(updatePlaceDTO.getId()).orElseThrow(
                    () -> new Exception("Post with  id = " + updatePlaceDTO.getId() + " not found")
            );
            place.setDescription(updatePlaceDTO.getDescription());
            place.setDateUpdated(LocalDate.now()); //check it
            place.setCity(updatePlaceDTO.getCity());
            place.setAddressLink(updatePlaceDTO.getAddressLink());
            placesRepository.save(place);
            return new ResponseEntity<>(place.getId(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public String updateMainImageForPlace(Long placeId, MultipartFile multipartFile) {
        Place place = placesRepository.findById(placeId).orElseThrow(
                () -> new NotFoundException("Place with" + placeId + "not found")
        );
        place.setMainFilePath(fileUploadService.saveFile(multipartFile));
        placesRepository.save(place);
        return "updated  main image for place with id " + placeId;
    }

    public ResponseEntity<Void> deletePlaceById(Long placeId) {
        Place place = placesRepository.findById(placeId).orElseThrow(
                () -> new NotFoundException("Place with id " + placeId + " not found")
        );
        place.setStatus(Status.DELETED_BY_ADMIN);
        place.setDateDeleted(LocalDateTime.now());
        placesRepository.save(place);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void ratePlace(Long placeId, RatingDTO ratingDto) {
        Place place = placesRepository.findById(placeId).orElseThrow(
                () -> new NotFoundException("Place with id " + placeId + " not found")
        );
        Rating rating = new Rating();
        rating.setRate(ratingDto.getRate());
        rating.setComment(ratingDto.getComment());
        rating.setPlace(place);
        rating.setUser(userRepository.getById(userService.getUserByAuthentication().getId()));
        ratingRepository.save(rating);
    }
}
