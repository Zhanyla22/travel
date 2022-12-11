package com.example.light_up_travel.controller;

import com.example.light_up_travel.model.GetPlaceDTO;
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
    @GetMapping("/get-all-active")
    public List<GetPlaceDTO> getAllActivePlaces(@RequestParam Integer page, @RequestParam Integer size){
        return placesService.getAll(page,size);
    }
}
