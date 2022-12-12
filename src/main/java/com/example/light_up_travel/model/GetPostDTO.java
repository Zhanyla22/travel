package com.example.light_up_travel.model;

import com.example.light_up_travel.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPostDTO {
    private Long id;
    private String description;
    private LocalDate dateCreated;
    private UserForPost user;
    private String filePath;
    private boolean like;
    private Long counter;
}
