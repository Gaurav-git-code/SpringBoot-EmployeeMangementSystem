package com.darknightcoder.ems.mapper;

import com.darknightcoder.ems.entity.Department;
import com.darknightcoder.ems.model.DepartmentDto;

public class DepartmentMapper {
    private DepartmentMapper(){}
    public static Department mapToDepartment(DepartmentDto departmentDto){
        Department department = new Department();
        department.setDepartmentName(departmentDto.getDepartmentName());
        department.setDepartmentType(departmentDto.getDepartmentType());
        return department;
    }

    public static DepartmentDto mapToDepartmentDto(Department department){
        return new DepartmentDto(
                department.getDepartmentId(),
                department.getDepartmentName(),
                department.getDepartmentType()
        );
    }
}
