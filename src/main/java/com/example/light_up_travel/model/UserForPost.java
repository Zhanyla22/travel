package com.example.light_up_travel.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserForPost {

    private Long id;
    private String fileUrl;
    private String name;
    private String surname;
}
