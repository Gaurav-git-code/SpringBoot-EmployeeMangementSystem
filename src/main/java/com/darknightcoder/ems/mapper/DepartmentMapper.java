package com.darknightcoder.ems.mapper;

import com.darknightcoder.ems.entity.Department;
import com.darknightcoder.ems.model.DepartmentDto;

public class DepartmentMapper {
    public static Department mapToDepartment(DepartmentDto departmentDto){
        return new Department (
                departmentDto.getDepartmentId(),
                departmentDto.getDepartmentName(),
                departmentDto.getDepartmentType()
                );
    }

    public static DepartmentDto mapToDepartmentDto(Department department){
        return new DepartmentDto(
                department.getDepartmentId(),
                department.getDepartmentName(),
                department.getDepartmentType()
        );
    }
}
