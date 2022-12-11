package com.example.light_up_travel.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserForPlaces {
    private Long ratingId;
    private String profileUrl;
    private String name;
    private String surname;
    private String comment;
    private Long rate;
}
