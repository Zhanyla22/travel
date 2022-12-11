package com.example.light_up_travel.mapper;

import com.example.light_up_travel.entity.Files;
import com.example.light_up_travel.entity.Place;
import com.example.light_up_travel.entity.Rating;
import com.example.light_up_travel.model.GetPlaceDTO;
import com.example.light_up_travel.model.UserForPlaces;
import com.example.light_up_travel.repository.PlacesRepository;
import com.example.light_up_travel.repository.RatingRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlacesMapper {

    public static GetPlaceDTO placeEntityToDTO(Place place, List<UserForPlaces> ratingList, List<String> filesList,Double rate){
        GetPlaceDTO placeDTO = new GetPlaceDTO();
        placeDTO.setDescription(place.getDescription());
        placeDTO.setMainFilePath(place.getMainFilePath());
        placeDTO.setName(place.getName());
        placeDTO.setRate(rate);
        placeDTO.setComments(ratingList);
        placeDTO.setFiles(filesList);
        return placeDTO;
    }

}
