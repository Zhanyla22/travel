package com.example.light_up_travel.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavouritesDto {
    private Long id;

    private String notes;

    private BasicUserDto user;

    private BasicPlaceDto place;
}
