package com.example.light_up_travel.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class SignupRequest {

  @NotBlank
  private String name;

  @NotBlank
  private String surname;

  @NotBlank
  @Size(max = 150)
  @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
  private String email;

  @JsonIgnore
  private Set<String> role;

  @NotBlank
  @Size(min = 8, max = 40)
  private String password;

}
