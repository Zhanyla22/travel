package com.example.light_up_travel.mapper;

import com.example.light_up_travel.dto.BasicPlaceDto;
import com.example.light_up_travel.entity.Place;


public class BasicPlaceMapper {
    public static BasicPlaceDto placeToBasicPlaceDto(Place place){
        BasicPlaceDto basicPlaceDto = new BasicPlaceDto();
        basicPlaceDto.setId(place.getId());
        basicPlaceDto.setName(place.getName());
        basicPlaceDto.setMainFilePath(place.getMainFilePath());
        return basicPlaceDto;
    }

    public static Place basicPlaceDtoToPlace(BasicPlaceDto basicPlaceDto){
        Place place = new Place();
        place.setId(basicPlaceDto.getId());
        place.setName(basicPlaceDto.getName());
        place.setMainFilePath(basicPlaceDto.getMainFilePath());
        return place;
    }
}
