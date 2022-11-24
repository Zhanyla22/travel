package com.example.light_up_travel.model;

import com.example.light_up_travel.entity.Role;
import com.example.light_up_travel.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "Will")
    private String name;

    @Schema(example = "Smith")
    private String surname;

    @Schema(example = "willsmith@gmail.com")
    @Size(max = 150)
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;


//    @Schema(example = "Male")
//    private String gender;
//
//    @Schema(example = "+923456789")
//    private String phoneNumber;
//
//    @Schema(example = "1995-09-11")
//    private LocalDate dob;
//
//    @Schema(example = "USA")
//    private String country;

    @Schema(example = "[\n" +
            "    \"user\"\n" +
            "  ]", description = "3 type of roles: \"user\", \"admin\", \"moderator\"")
    private Set<String> role;

//    @JsonIgnore
//    private Date dateUpdated;
}