package com.darknightcoder.ems.mapper;

import com.darknightcoder.ems.entity.Department;
import com.darknightcoder.ems.entity.Employee;
import com.darknightcoder.ems.model.EmployeeDto;

public class EmployeeMapper {

    public static EmployeeDto maptoEmployeeDto(Employee employee){
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartment().getDepartmentId()
        );
    }

    public static Employee mapToEmployee(EmployeeDto employeeDto, Department department){
        return new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail(),
                department
        );
    }
}
