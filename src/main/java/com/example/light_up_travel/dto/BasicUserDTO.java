package com.example.light_up_travel.dto;

import com.example.light_up_travel.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
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
