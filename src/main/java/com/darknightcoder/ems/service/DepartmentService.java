package com.darknightcoder.ems.service;

import com.darknightcoder.ems.model.DepartmentDto;
import com.darknightcoder.ems.model.DepartmentEmployeesDto;
import com.darknightcoder.ems.model.DepartmentResponse;

public interface DepartmentService {
    DepartmentDto createDepartment(DepartmentDto departmentDto);
    DepartmentResponse getAllDepartment(int pageNo, int pageSize, String sortBy, String sortDir);
    DepartmentDto getDepartmentById(long id);
    DepartmentEmployeesDto getAllEmployeeForDepartment(long departmentId, int pageNo, int pageSize, String sortBy, String sortDir);
    DepartmentDto updateDepartment(long id, DepartmentDto departmentDto);
    void deleteDepartment(long departmentId);

}
