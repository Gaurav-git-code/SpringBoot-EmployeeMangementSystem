package com.darknightcoder.ems.service;

import com.darknightcoder.ems.model.EmployeeDto;
import com.darknightcoder.ems.model.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    //create Employee
    EmployeeDto createEmployee(EmployeeDto employeeDto, long departmentId);
    //get employee
    EmployeeDto getEmployeeById(long departmentId,long employeeId);
    //get all employee
    EmployeeResponse getAllEmployee(long departmentId, int pageNo, int pageSize, String sortBy, String sortDir);
    //update employee
    EmployeeDto updateEmployee(long departmentId, long employeeId, EmployeeDto employeeDto);
    //delete Employee
    void deleteEmployee(long departmentId, long employeeId);


}
