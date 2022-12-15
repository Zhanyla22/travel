package com.example.light_up_travel.dto;


import com.example.light_up_travel.entity.User;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostDTO {

    private String description;

    private LocalDate dateCreated;

    private User user;
}
