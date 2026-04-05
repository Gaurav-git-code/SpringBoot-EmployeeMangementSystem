package com.darknightcoder.ems.repository;

import com.darknightcoder.ems.entity.Department;
import com.darknightcoder.ems.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;



@DataJpaTest
class EmployeeRepositoryTest {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    //Junit test case for
    @Test
    public void givenDepartmentAndPageable_whenFindAllByDepartment_thenReturnPageOfEmployee(){
        //given - precondition or setup
        Department department = new Department(null,"Research","B2B");
        departmentRepository.save(department);
        Employee employee = new Employee(null, "gaurav","suman","gaurav@gmail.com",department);
        employeeRepository.save(employee);
        Pageable pageable = PageRequest.of(0,1, Sort.by("id").ascending());
        //Page<Employee> page = new PageImpl<>(List.of(employee),pageable,List.of(employee).size());

        //when -action or behaviour which we are going to test
        Page<Employee> employeePage = employeeRepository.findAllByDepartment(department,pageable);

        //then - verify the output
        assertThat(employeePage).isNotNull();
        assertThat(employeePage.getTotalPages()).isEqualTo(1);
        assertThat(employeePage.getTotalElements()).isEqualTo(1);
        assertThat(employeePage.getContent().get(0).getFirstName()).isEqualTo("gaurav");

    }

    //Junit test case for
    @Test
    public void givenDepartment_whenCountByDepartment_thenReturnCount(){
        //given - precondition or setup
        Department department = new Department(null,"Research","B2B");
        departmentRepository.save(department);
        Employee employee = new Employee(null, "gaurav","suman","gaurav@gmail.com",department);
        employeeRepository.save(employee);
        //when -action or behaviour which we are going to test
        int count = employeeRepository.countByDepartment(department);

        //then - verify the output
        assertThat(count).isEqualTo(1);
    }

}