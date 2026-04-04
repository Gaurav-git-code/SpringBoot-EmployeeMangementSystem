package com.darknightcoder.ems.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {
    private long departmentId;
    @NotBlank(message = "Department name cannot be empty!")
    @Size(min = 2, max = 20)
    private String departmentName;
    @NotBlank(message = "Department type cannot be empty!")
    @Size(min = 2, max = 20)
    private String departmentType;
}
