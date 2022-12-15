package com.example.light_up_travel.controller;

import com.example.light_up_travel.dto.*;
import com.example.light_up_travel.services.impl.PlacesService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping("/places")

public class PlacesController {

    @Autowired
    private PlacesService placesService;



    @Operation(summary =  "Получение places по айдишке ")
    @GetMapping("/get-by-id/{placeId}")
    public ResponseEntity<GetPlaceDTO> getPlacesById(@PathVariable Long placeId) throws Exception {
        return placesService.getActivePlaceById(placeId);
    }

    @Operation(summary =  "Получение всего активного places о категориям")
    @GetMapping("/get-all-active-by-category/{categoryId}")
    public List<GetPlaceWithCategDTO> getAllActiveByCategoryPlaces(@PathVariable int categoryId) throws Exception {
        return placesService.getAllPlacesByCategory(categoryId);
    }

    @Operation(summary = "поиск по имени в плейсес")
    @GetMapping("/get-place-by-name/{placeName}")
    public ResponseEntity<GetPlaceByNameDTO> getPlaceByName(@PathVariable String placeName){
        return placesService.getPlaceByName(placeName);
    }

    @Operation(summary = "создать новый place без фото/ 1 запрос")
    @PostMapping("/create-new-place-without-photo/{categoryId}")
    public ResponseEntity<Long> createNewPlace(@PathVariable int categoryId,@RequestBody CreatePlaceDto createPlaceDto) throws Exception{
        return placesService.createNewPlace(categoryId,createPlaceDto);
    }

    @Operation(summary = "создать main photo place / 2й запрос")
    @PostMapping("/create-new-place-main-photo/{placeId}")
    public ResponseEntity<String> createNewPlaceMainPhoto(@PathVariable Long placeId,
                                                        @RequestPart MultipartFile multipartFile) throws Exception{
        return ResponseEntity.ok(placesService.saveMainImageForPlace(placeId,multipartFile));
    }

    @Operation(summary = "обновить place без фото 1 запрос")
    @PutMapping("/update-place") // ?????((((((
    public ResponseEntity<Long> updatePlace(@RequestBody UpdatePlaceDTO updatePlaceDTO) throws Exception{
        return placesService.updatePlace(updatePlaceDTO);
    }

    @Operation(summary = "обновить main-image on place with id 2 запрос")
    @PutMapping("/update-place-main-image/{placeId}") // ?????((((((
    public ResponseEntity<String> updateMainImageForPlace(@PathVariable Long placeId,
                                                @RequestPart MultipartFile multipartFile) throws Exception{
        return ResponseEntity.ok(placesService.updateMainImageForPlace(placeId,multipartFile));
    }

    @Operation(summary = "удалить place по айдишке")
    @DeleteMapping("/delete-place/{placeId}") //working good
    public void deletePlaceById(@PathVariable Long placeId) throws Exception{
        placesService.deletePlaceById(placeId);
    }

    @Operation(summary = "Rate place")
    @PostMapping("/rate/{placeId}")
    public void ratePlace(@PathVariable Long placeId, @RequestBody RatingDto ratingDto){

        placesService.ratePlace(placeId,ratingDto);
    }

}
