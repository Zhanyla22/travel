package com.example.light_up_travel.dto;


import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavouritesDTO {
    private Long id;

    private String notes;

    private BasicUserDTO user;

    private BasicPlaceDTO place;
}
