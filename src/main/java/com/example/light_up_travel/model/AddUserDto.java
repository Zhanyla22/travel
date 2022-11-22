package com.example.light_up_travel.model;

import com.example.light_up_travel.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddUserDto {

    @NotBlank(message = "Name can't be null or empty")
    @Schema(example = "John", required = true)
    private String name;

    @NotBlank(message = "Surname can't be null or empty")
    @Schema(example = "Smith", required = true)
    private String surname;

    @NotBlank(message = "Email can't be null or empty")
    @Schema(example = "johnsmith@gmail.com", required = true)
    @Size(max = 150)
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Schema(example = "password123", required = true)
    @NotBlank
    @Size(min = 8, max = 40)
    private String password;

    @Schema(example = "Male")
    private String gender;

    @Schema(example = "+123456789")
    private String phoneNumber;

    @Schema(example = "1995-11-11")
    private LocalDate dob;

    @Schema(example = "USA")
    private String country;

    @NotNull
    @Schema(example = "[\n" +
            "    \"admin\"\n" +
            "  ]", description = "3 type of roles: \"user\", \"admin\", \"moderator\"", required = true)
    private Set<String> role;

//    @JsonIgnore
//    private String verificationCode;
//
//    @JsonIgnore
//    private boolean enabled;
//
//    @JsonIgnore
//    private Date dateCreated;
//
//    @JsonIgnore
//    private Status status;
}