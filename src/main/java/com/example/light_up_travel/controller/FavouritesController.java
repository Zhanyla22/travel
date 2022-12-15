package com.example.light_up_travel.controller;


import com.example.light_up_travel.dto.CommentsDto;
import com.example.light_up_travel.dto.FavouritesDto;
import com.example.light_up_travel.dto.UpdateUserDto;
import com.example.light_up_travel.payload.response.MessageResponse;
import com.example.light_up_travel.services.impl.FavouriteServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/favourite")
public class FavouritesController {

    private final FavouriteServiceImpl favouriteService;

    @Operation(summary = "Add new favourite place")
    @PostMapping("/add")
    public ResponseEntity<?> addFavourite(@RequestBody FavouritesDto favouriteDto) {
        try{
            favouriteService.addFavourite(favouriteDto);
            return ResponseEntity.ok(new MessageResponse("Favourite place is added successfully"));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @Operation(summary = "Get not deleted favourite place by id")
    @GetMapping("/{id}")
    ResponseEntity<?> getNotDeletedFavouriteById(@PathVariable Long id){
        try{
            return ResponseEntity.ok(favouriteService.getFavouriteById(id));
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @Operation(summary = "Get all not deleted favourite places of single user")
    @GetMapping("/all-not-deleted-favourites-by-user-id/{userId}")
    ResponseEntity<?> getAllNotDeletedCommentsByPostId(@PathVariable Long userId) {
        try{
            return ResponseEntity.ok(favouriteService.getAllNotDeletedFavouritesByUserId(userId));
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @Operation(summary = "Delete not deleted favourite place by id for user")
    @DeleteMapping("/delete-favourite-place-by-id/{favouriteId}")
    ResponseEntity<?> deleteNotDeletedCommentByIdForUser(@PathVariable Long favouriteId){
        try {
            return ResponseEntity.ok(favouriteService.deleteNotDeletedFavouriteById(favouriteId));
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

    @Operation(summary = "Update not deleted favourite place by id")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUserById(@RequestBody FavouritesDto favouritesDto, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(favouriteService.updateNotDeletedFavouriteById(id, favouritesDto));
        } catch (NotFoundException nfe) {
            throw new NotFoundException(nfe.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }
}
