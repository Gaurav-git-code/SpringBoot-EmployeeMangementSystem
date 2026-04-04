package com.darknightcoder.ems.model;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private long id;
    @NotBlank(message = "first Name cannot be empty!")
    @Size(min = 2, max = 20)
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Cannot contain special character!")
    private String firstName;
    @NotBlank(message = "last Name cannot be empty!")
    @Size(min = 1, max = 20)
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Cannot contain special character!")
    private String lastName;
    @Email(message = "email should be valid!")
    @NotBlank(message = "email cannot be empty!")
    private String email;
    @Positive(message = "department id must be positive!")
    @NotNull(message = "department id is required!")
    private Long departmentId;
}
