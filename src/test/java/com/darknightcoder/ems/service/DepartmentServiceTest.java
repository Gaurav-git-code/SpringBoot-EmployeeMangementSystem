package com.darknightcoder.ems.service;

import com.darknightcoder.ems.entity.Department;
import com.darknightcoder.ems.entity.Employee;
import com.darknightcoder.ems.model.DepartmentDto;
import com.darknightcoder.ems.model.DepartmentEmployeesDto;
import com.darknightcoder.ems.model.DepartmentResponse;
import com.darknightcoder.ems.repository.DepartmentRepository;
import com.darknightcoder.ems.repository.EmployeeRepository;
import com.darknightcoder.ems.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private DepartmentServiceImpl departmentService;

    private Department savedDepartment;

    @BeforeEach
    void setUp(){
        this.savedDepartment = new Department(1L, "Research","B2B");
    }

    //Junit test case for create department
    @Test
    @DisplayName("Junit test case for create department")
    public void givenDepartment_whenCreateDepartment_thenReturnDepartmentDto(){
        //given - precondition or setup
        DepartmentDto departmentDto = DepartmentDto.builder()
                .departmentId(1L)
                .departmentName("Research")
                .departmentType("B2B")
                .build();
        //this.savedDepartment = new Department(1L, "Research","B2B");

        BDDMockito.given(departmentRepository.save(ArgumentMatchers.any(Department.class)))
                .willReturn(savedDepartment);


        //when -action or behaviour which we are going to test
        DepartmentDto savedDepartmentDto = departmentService.createDepartment(departmentDto);

        //then - verify the output
        assertThat(savedDepartmentDto).isNotNull();
        assertThat(savedDepartmentDto.getDepartmentName()).isEqualTo("Research");
        assertThat(savedDepartmentDto.getDepartmentType()).isEqualTo("B2B");
    }

    //Junit test case for fetching all department
    @Test
    @DisplayName("Junit test case for fetching all department")
    public void givenDepartmentList_whenGetAllDepartment_thenReturnDepartmentList(){
        //given - precondition or setup


        List<Department> listOfDepartment = List.of(savedDepartment);
        Pageable pageable =PageRequest.of(0,1,Sort.by("departmentName").ascending());
        Page<Department> depatmentPage = new PageImpl<>(listOfDepartment,pageable,listOfDepartment.size());

        //when -action or behaviour which we are going to test
        BDDMockito.given(departmentRepository.findAll(ArgumentMatchers.any(Pageable.class)))
                .willReturn(depatmentPage);
        DepartmentResponse departmentResponse = departmentService.getAllDepartment(0,1,"departmentName","ASC");
        //then - verify the output

        assertThat(departmentResponse).isNotNull();
        assertThat(departmentResponse.getDepartments().get(0).getDepartmentId()).isEqualTo(1);
        assertThat(departmentResponse.getDepartments().get(0).getDepartmentName()).isEqualTo("Research");
        assertThat(departmentResponse.getDepartments().get(0).getDepartmentType()).isEqualTo("B2B");
        assertThat(departmentResponse.getPageNo()).isEqualTo(0);
        assertThat(departmentResponse.getPageSize()).isEqualTo(1);
        assertThat(departmentResponse.getTotalElement()).isEqualTo(1);
        assertThat(departmentResponse.getTotalPage()).isEqualTo(1);
        assertThat(departmentResponse.isLast()).isTrue();

    }

    //Junit test case for fetching department by id
    @Test
    @DisplayName("Junit test case for fetching department by id")
    public void givenDepartment_whenGetDepartmentById_thenReturnDepartment(){
        //given - precondition or setup

        //when -action or behaviour which we are going to test
        BDDMockito.given(departmentRepository.findById(ArgumentMatchers.eq(1L)))
                .willReturn(Optional.of(savedDepartment));
        DepartmentDto departmentDto = departmentService.getDepartmentById(1L);
        //then - verify the output
        assertThat(departmentDto).isNotNull();
        assertThat(departmentDto.getDepartmentName()).isEqualTo("Research");
        assertThat(departmentDto.getDepartmentType()).isEqualTo("B2B");
    }

    //Junit test case for fetching all employee for a department
    @Test
    @DisplayName("Junit test case for fetching all employee for a department")
    public void givenDepartment_whenGetAllEmployeeForDepartment_thenReturnEmployeePaginated(){
        //given - precondition or setup


        Employee employee = new Employee(1L, "Gaurav", "Suman","gaurav@gmail.com",savedDepartment);
        Pageable pageable = PageRequest.of(0,1,Sort.by("departmentName").ascending());
        Page<Employee> page = new PageImpl<>(List.of(employee),pageable,List.of(employee).size());
        BDDMockito.given(departmentRepository.findById(ArgumentMatchers.eq(1L)))
                .willReturn(Optional.of(savedDepartment));
        BDDMockito.given(employeeRepository.findAllByDepartment(ArgumentMatchers.eq(savedDepartment),
                        ArgumentMatchers.any(Pageable.class)))
                .willReturn(page);

        //when -action or behaviour which we are going to test
        DepartmentEmployeesDto departmentEmployeesDto =
                departmentService.getAllEmployeeForDepartment(1L,0,1,"departmentName","ASC");

        //then - verify the output
        assertThat(departmentEmployeesDto).isNotNull();
        assertThat(departmentEmployeesDto.getDepartmentId()).isEqualTo(1L);
        assertThat(departmentEmployeesDto.getDepartmentName()).isEqualTo("Research");
        assertThat(departmentEmployeesDto.getDepartmentType()).isEqualTo("B2B");
        assertThat(departmentEmployeesDto.getListOfEmployee().get(0).getId()).isEqualTo(1L);
        assertThat(departmentEmployeesDto.getListOfEmployee().get(0).getFirstName()).isEqualTo("Gaurav");
        assertThat(departmentEmployeesDto.getListOfEmployee().get(0).getLastName()).isEqualTo("Suman");
        assertThat(departmentEmployeesDto.getListOfEmployee().get(0).getEmail()).isEqualTo("gaurav@gmail.com");
        assertThat(departmentEmployeesDto.getPageNo()).isEqualTo(0);
        assertThat(departmentEmployeesDto.getPageSize()).isEqualTo(1);
        assertThat(departmentEmployeesDto.getTotalPages()).isEqualTo(1);
        assertThat(departmentEmployeesDto.getTotalElement()).isEqualTo(1);
        assertThat(departmentEmployeesDto.isLast()).isTrue();
    }

    //Junit test case for updating department
    @Test
    @DisplayName("Junit test case for updating department")
    public void givenDepartment_whenUpdateDepartment_thenReturnDepartmentDto(){
        //given - precondition or setup
        DepartmentDto departmentDto = DepartmentDto.builder()
                .departmentId(1)
                .departmentName("Finance")
                .departmentType("B2C")
                .build();


        //when -action or behaviour which we are going to test
        BDDMockito.given(departmentRepository.findById(1L))
                        .willReturn(Optional.of(savedDepartment));
        savedDepartment.setDepartmentName("Finance");
        savedDepartment.setDepartmentType("B2C");
        BDDMockito.given(departmentRepository.save(ArgumentMatchers.any()))
                .willReturn(savedDepartment);
        DepartmentDto updatedDepartmentDto = departmentService.updateDepartment(1L,departmentDto);
        //then - verify the output
        assertThat(updatedDepartmentDto).isNotNull();
        assertThat(updatedDepartmentDto.getDepartmentId()).isEqualTo(1);
        assertThat(updatedDepartmentDto.getDepartmentName()).isEqualTo("Finance");
        assertThat(updatedDepartmentDto.getDepartmentType()).isEqualTo("B2C");
    }

    //Junit test case for deleting department
    @Test
    @DisplayName("Junit test case for deleting department")
    public void givenDepartment_whenDeleteDepartment_thenReturnNothing(){
        //given - precondition or setup
        //when -action or behaviour which we are going to test
        BDDMockito.given(departmentRepository.findById(1L)).willReturn(Optional.of(savedDepartment));
        BDDMockito.given(employeeRepository.countByDepartment(savedDepartment))
                        .willReturn(0);
        BDDMockito.willDoNothing().given(departmentRepository).deleteById(1L);
        departmentService.deleteDepartment(1L);
        //then - verify the output
        BDDMockito.verify(departmentRepository, Mockito.times(1)).deleteById(1L);
    }

}