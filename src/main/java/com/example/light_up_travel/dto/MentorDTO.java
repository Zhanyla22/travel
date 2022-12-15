package com.example.light_up_travel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MentorDTO {

    private Long menteeId;

    private Long mentorId;
}
