package com.example.light_up_travel.model;


import com.example.light_up_travel.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreatePostDTO {

    private Long id;
    private String description;
    private LocalDate dateCreated;
    private User user;
    private String filePath;
}
