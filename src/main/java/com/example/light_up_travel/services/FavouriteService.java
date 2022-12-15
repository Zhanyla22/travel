package com.example.light_up_travel.services;

import com.example.light_up_travel.dto.FavouritesDTO;
import com.example.light_up_travel.entity.Favourite;
import com.example.light_up_travel.exceptions.NotFoundException;
import com.example.light_up_travel.mapper.BasicPlaceMapper;
import com.example.light_up_travel.mapper.FavouritesMapper;
import com.example.light_up_travel.repository.FavouritesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavouriteService {

    private final FavouritesRepository favouritesRepository;

    private final UserService userService;


    public FavouritesDTO addFavourite(FavouritesDTO favouritesDto){
        Favourite favourite = new Favourite();
        favourite.setNotes(favouritesDto.getNotes());
        favourite.setPlace(BasicPlaceMapper.basicPlaceDtoToPlace(favouritesDto.getPlace()));
        favourite.setUser(userService.getUserByAuthentication());
        favourite.setDateCreated(new Date());
        return FavouritesMapper.favouritesToFavouritesDto(favouritesRepository.save(favourite));
    }

    public List<FavouritesDTO> getAllNotDeletedFavouritesByUserId(Long userId){
        Iterable<Favourite> favourites = favouritesRepository.findAllNotDeletedFavouriteByUserId(userId);
        List<FavouritesDTO> favouriteDto = new ArrayList<>();

        for (Favourite favourite : favourites){
            favouriteDto.add(FavouritesMapper.favouritesToFavouritesDto(favourite));
        }
        return favouriteDto;
    }

    public FavouritesDTO getFavouriteById(Long id){
        Favourite favourite = isFavouritesDeleted(id);
        return FavouritesMapper.favouritesToFavouritesDto(favourite);
    }

    public String deleteNotDeletedFavouriteById(Long id){
        Favourite favourite = isFavouritesDeleted(id);
        if (favourite.getUser() == userService.getUserByAuthentication()){
            favourite.setDateDeleted(new Date());
            favouritesRepository.save(favourite);
            return "Favourite place with id: " + id + " deleted successfully";
        }
        else throw new NotFoundException("User doesn't have a favourite place with id: " + id);
    }

    public FavouritesDTO updateNotDeletedFavouriteById(Long id, FavouritesDTO favouritesDto){
        Favourite favourite = isFavouritesDeleted(id);
        if (favourite.getUser() == userService.getUserByAuthentication()){
            favourite.setNotes(favouritesDto.getNotes());
            favourite.setDateUpdated(new Date());
            return FavouritesMapper.favouritesToFavouritesDto(favouritesRepository.save(favourite));
        }else {
             throw new NotFoundException("User doesn't have a favourite place with id: " + id);
        }
    }


    public Favourite isFavouritesDeleted(Long id){
        Favourite favourite = favouritesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find favourite place with id: " + id));
        if (favourite.getDateDeleted() != null){
            throw new NotFoundException("Favourite place with id: " + id + " was deleted!");
        }
        return favourite;
    }
}
