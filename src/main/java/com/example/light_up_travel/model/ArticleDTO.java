package com.example.light_up_travel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

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
