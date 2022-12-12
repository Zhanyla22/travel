package com.example.light_up_travel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPlaceDTO {
    private String mainFilePath;
    private String name;
    private Double rate;
    private List<UserForPlaces> comments;
    private List<String> files;
    private String description;
}
