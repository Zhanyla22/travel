package com.example.light_up_travel.dto;

import com.example.light_up_travel.enums.Stat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForumDto {

    private Long id;

    private String description;

    private BasicUserDto user;

    private Stat status;

    private Date dateCreated;

    //    private String wayOfContacting;

}
