package com.example.light_up_travel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MentorDto {
    private Long menteeId;

    private Long mentorId;
}
