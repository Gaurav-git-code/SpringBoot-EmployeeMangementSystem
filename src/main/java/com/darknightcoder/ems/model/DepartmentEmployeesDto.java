package com.darknightcoder.ems.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentEmployeesDto {
    private long departmentId;
    private String departmentName;
    private String departmentType;
    private List<EmployeeDto> listOfEmployee;
    private int pageNo;
    private int pageSize;
    private int totalElement;
    private int totalPages;
    private boolean isLast;
}
