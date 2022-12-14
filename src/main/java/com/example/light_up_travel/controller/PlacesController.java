package com.example.light_up_travel.controller;

import com.example.light_up_travel.dto.GetPlaceDTO;
import com.example.light_up_travel.dto.GetPlaceWithCategDTO;
import com.example.light_up_travel.services.impl.PlacesService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/places")

public class PlacesController {

    @Autowired
    private PlacesService placesService;



    @Operation(summary =  "Получение всего активного places ")
    @GetMapping("/get-all-active/{categoryId}")
    public List<GetPlaceDTO> getAllActivePlaces(@RequestParam Integer page, @RequestParam Integer size,@PathVariable Long categoryId) throws Exception {
        return placesService.getAll(page,size,categoryId);
    }

    @Operation(summary =  "Получение всего активного places о категориям")
    @GetMapping("/get-all-active-by-category/{categoryId}")
    public List<GetPlaceWithCategDTO> getAllActiveByCategoryPlaces(@PathVariable int categoryId) throws Exception {
        return placesService.getAllPlacesByCategory(categoryId);
    }
}
