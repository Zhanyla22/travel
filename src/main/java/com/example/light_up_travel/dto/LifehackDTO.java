package com.example.light_up_travel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LifehackDTO {
    private Long id;
    private String title;
    private String description;
    private String filePath;
}
