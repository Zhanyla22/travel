package com.example.light_up_travel.model;

import com.example.light_up_travel.enums.Stat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicFormDto {

    private String description;

    private Stat status;

}
