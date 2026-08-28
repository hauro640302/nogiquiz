package com.example.demo.login.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import lombok.Data;

@Data
public class PlayerForm {
  @NotBlank
  @Length(min = 3, max = 14)
  @Pattern(regexp = "^[a-zA-Z0-9]+$")
  String name;
  @NotBlank
  @Length(min = 3, max = 14)
  @Pattern(regexp = "^[a-zA-Z0-9]+$")
  String password;

  Boolean result;
  String message;
}


