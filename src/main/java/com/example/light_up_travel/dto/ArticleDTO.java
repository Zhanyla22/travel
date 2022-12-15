package com.example.light_up_travel.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {

    private Long id;

    private String title;

    private LocalDate dateCreated;

    private String filePath;

    private String description;

    private String subtitle;

    private String text;

}
