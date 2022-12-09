package com.example.light_up_travel.model;

import com.example.light_up_travel.entity.User;
import com.example.light_up_travel.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CheckPostCencorshipDTO {
    private Long id;
    private String description;
    private LocalDate createdDate;
    private Status status;
    private User user;
}
