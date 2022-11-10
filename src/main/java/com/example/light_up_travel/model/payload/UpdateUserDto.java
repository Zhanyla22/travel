package com.example.light_up_travel.model.payload;

import com.example.light_up_travel.entity.Role;
import com.example.light_up_travel.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserDto {

//    private Long id;

    private String name;

    private String surname;

    private String email;

    private String password;

    private String gender;

    private String phoneNumber;

    private LocalDate dob;

    private String country;

    private Set<String> role;

    private Date dateUpdated;

}