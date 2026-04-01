package com.darknightcoder.ems.repository;

import com.darknightcoder.ems.entity.Department;
import com.darknightcoder.ems.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Page<Employee> findAllByDepartment(Department department, Pageable pageable);
    int countByDepartment(Department department);
}
