package com.example.light_up_travel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArticleDTO {

    private Long id;

    private String title;

    private LocalDate dateCreated;

    private String filePath;

    private String description;

    private String subtitle;

    private String text;

}
