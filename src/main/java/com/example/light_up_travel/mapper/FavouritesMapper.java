package com.example.light_up_travel.mapper;

import com.example.light_up_travel.dto.FavouritesDto;
import com.example.light_up_travel.entity.Favourite;

public class FavouritesMapper {
    public static FavouritesDto favouritesToFavouritesDto(Favourite favourite){
        FavouritesDto favouritesDto = new FavouritesDto();
        favouritesDto.setId(favourite.getId());
        favouritesDto.setNotes(favourite.getNotes());
        favouritesDto.setUser(BasicUserMapper.basicUserToUserDTO(favourite.getUser()));
        favouritesDto.setPlace(BasicPlaceMapper.placeToBasicPlaceDto(favourite.getPlace()));
        return favouritesDto;
    }

    public static Favourite favouritesToFavourite(FavouritesDto favouriteDto){
        Favourite favourite = new Favourite();
        favourite.setId(favouriteDto.getId());
        favourite.setNotes(favouriteDto.getNotes());
        favourite.setUser(BasicUserMapper.basicUserDtoToUser(favouriteDto.getUser()));
        favourite.setPlace(BasicPlaceMapper.basicPlaceDtoToPlace(favouriteDto.getPlace()));
        return favourite;
    }

}
