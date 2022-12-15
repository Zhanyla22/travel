package com.example.light_up_travel.dto;

import com.example.light_up_travel.entity.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasicUserDTO {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private String mentorName;

    private Set<Role> roles;

}
