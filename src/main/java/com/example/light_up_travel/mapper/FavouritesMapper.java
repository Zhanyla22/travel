package com.example.light_up_travel.mapper;

import com.example.light_up_travel.dto.FavouritesDTO;
import com.example.light_up_travel.entity.Favourite;

public class FavouritesMapper {
    public static FavouritesDTO favouritesToFavouritesDto(Favourite favourite){
        FavouritesDTO favouritesDto = new FavouritesDTO();
        favouritesDto.setId(favourite.getId());
        favouritesDto.setNotes(favourite.getNotes());
        favouritesDto.setUser(BasicUserMapper.basicUserToUserDTO(favourite.getUser()));
        favouritesDto.setPlace(BasicPlaceMapper.placeToBasicPlaceDto(favourite.getPlace()));
        return favouritesDto;
    }

    public static Favourite favouritesToFavourite(FavouritesDTO favouriteDto){
        Favourite favourite = new Favourite();
        favourite.setId(favouriteDto.getId());
        favourite.setNotes(favouriteDto.getNotes());
        favourite.setUser(BasicUserMapper.basicUserDtoToUser(favouriteDto.getUser()));
        favourite.setPlace(BasicPlaceMapper.basicPlaceDtoToPlace(favouriteDto.getPlace()));
        return favourite;
    }

}
