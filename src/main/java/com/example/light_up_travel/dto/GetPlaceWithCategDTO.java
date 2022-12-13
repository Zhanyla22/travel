package com.example.light_up_travel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPlaceWithCategDTO {
    private Long placeId;
    private String mainFilePath;
    private String name;
    private Double rate;
    private int categoryId;
    private String categoryName;
    private int parentId;
    private int parentName;
}
