package com.example.light_up_travel.mapper;

import com.example.light_up_travel.dto.GetPlaceWithCategDTO;
import com.example.light_up_travel.entity.Category;
import com.example.light_up_travel.entity.Place;
import com.example.light_up_travel.entity.PlaceCategories;
import org.springframework.stereotype.Component;

@Component
public class GetPlaceWithCategoryMapper {


    public  static GetPlaceWithCategDTO PlaceEntityToPlaceDTO(Place place,Double rate){
        GetPlaceWithCategDTO getPlaceWithCategDTO = new GetPlaceWithCategDTO();
        getPlaceWithCategDTO.setName(place.getName());
        getPlaceWithCategDTO.setMainFilePath(place.getMainFilePath());
        getPlaceWithCategDTO.setRate(rate);


        return getPlaceWithCategDTO;

    }
}
