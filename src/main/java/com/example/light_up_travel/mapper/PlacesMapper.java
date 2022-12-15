package com.example.light_up_travel.mapper;

import com.example.light_up_travel.entity.Place;
import com.example.light_up_travel.dto.GetPlaceDTO;
import com.example.light_up_travel.dto.UserForPlacesDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlacesMapper {

    public static GetPlaceDTO placeEntityToDTO(Place place, List<UserForPlacesDTO> ratingList, List<String> filesList, Double rate){
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
