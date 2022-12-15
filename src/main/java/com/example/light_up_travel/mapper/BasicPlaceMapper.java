package com.example.light_up_travel.mapper;

import com.example.light_up_travel.dto.BasicPlaceDTO;
import com.example.light_up_travel.entity.Place;


public class BasicPlaceMapper {
    public static BasicPlaceDTO placeToBasicPlaceDto(Place place){
        BasicPlaceDTO basicPlaceDto = new BasicPlaceDTO();
        basicPlaceDto.setId(place.getId());
        basicPlaceDto.setName(place.getName());
        basicPlaceDto.setMainFilePath(place.getMainFilePath());
        return basicPlaceDto;
    }

    public static Place basicPlaceDtoToPlace(BasicPlaceDTO basicPlaceDto){
        Place place = new Place();
        place.setId(basicPlaceDto.getId());
        place.setName(basicPlaceDto.getName());
        place.setMainFilePath(basicPlaceDto.getMainFilePath());
        return place;
    }
}
