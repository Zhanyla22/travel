package com.example.light_up_travel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AddUserDTO {

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

    @NotNull
    @Schema(example = "[\n" +
            "    \"admin\"\n" +
            "  ]", description = "3 type of roles: \"user\", \"admin\", \"moderator\"", required = true)
    private Set<String> role;

}