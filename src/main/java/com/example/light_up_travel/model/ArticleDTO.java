package com.example.light_up_travel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArticleDTO {

    private Long id;

    private String title;

    private LocalDateTime dateCreated;

    private String filePath;

    private String description;

    private String subtitle;

    private String text;

}
