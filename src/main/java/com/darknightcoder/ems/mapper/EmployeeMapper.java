package com.darknightcoder.ems.mapper;

import com.darknightcoder.ems.entity.Department;
import com.darknightcoder.ems.entity.Employee;
import com.darknightcoder.ems.model.EmployeeDto;

public class EmployeeMapper {

    private EmployeeMapper (){}

    public static EmployeeDto mapToEmployeeDto(Employee employee){
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartment().getDepartmentId()
        );
    }

    public static Employee mapToEmployee(EmployeeDto employeeDto, Department department){
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setDepartment(department);
        return employee;
    }
}
