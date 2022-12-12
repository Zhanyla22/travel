package com.example.light_up_travel.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {

    @Schema(example = "Will")
    private String name;

    @Schema(example = "Smith")
    private String surname;

    @Schema(example = "willsmith@gmail.com")
    @Size(max = 150)
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Schema(example = "password123")
    @NotBlank
    @Size(min = 8, max = 40)
    private String password;

    @Schema(example = "Male")
    private String gender;

    @Schema(example = "+923456789")
    private String phoneNumber;

    @Schema(example = "1995-09-11")
    private LocalDate dob;

    @Schema(example = "USA")
    private String country;
}