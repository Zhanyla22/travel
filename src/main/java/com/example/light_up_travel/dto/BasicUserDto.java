package com.example.light_up_travel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicUserDto {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private String mentorName;

}
