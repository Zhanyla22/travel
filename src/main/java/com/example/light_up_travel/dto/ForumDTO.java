package com.example.light_up_travel.dto;

import com.example.light_up_travel.enums.Stat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForumDTO {

    private Long id;

    private String description;

    private BasicUserDTO user;

    private Stat status;

    private Date dateCreated;

    //    private String wayOfContacting;

}
