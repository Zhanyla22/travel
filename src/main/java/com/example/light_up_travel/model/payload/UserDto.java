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
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    private String name;

    private String surname;

    @Size(max = 150)
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @NotBlank
    @Size(min = 8, max = 40)
    private String password;

    private String gender;

    private String phoneNumber;

    private LocalDate dob;

    private String country;

    private Set<String> role;

    private String verificationCode;

    private boolean enabled;

    private Date dateCreated;

    private Status status;

}