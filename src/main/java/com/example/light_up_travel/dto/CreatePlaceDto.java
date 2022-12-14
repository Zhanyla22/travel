package com.example.light_up_travel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePlaceDto {
    private String name;
    private String city;
    private String description;
    private String addressLink;
}
