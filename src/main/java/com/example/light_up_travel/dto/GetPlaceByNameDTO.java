package com.example.light_up_travel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPlaceByNameDTO {

    private String mainFilePath;

    private String name;

    private Double rate;
}
