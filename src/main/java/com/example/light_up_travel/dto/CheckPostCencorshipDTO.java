package com.example.light_up_travel.dto;

import com.example.light_up_travel.entity.User;
import com.example.light_up_travel.enums.Status;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CheckPostCencorshipDTO {
    private Long id;

    private String description;

    private LocalDate createdDate;

    private Status status;

    private User user;
}
