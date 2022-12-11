package com.example.light_up_travel.controller;

import com.example.light_up_travel.model.GetPlaceDTO;
import com.example.light_up_travel.model.LifehackDTO;
import com.example.light_up_travel.repository.PlacesRepository;
import com.example.light_up_travel.services.impl.PlacesService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlacesController {

    private PlacesService placesService;

    @Operation(summary =  "Получение всего активного places ")
    @GetMapping("/get-all-active-places")
    public List<GetPlaceDTO> getAllActivePlaces(@RequestParam Integer page, @RequestParam Integer size){
        return placesService.getAll(page,size);
    }
}
