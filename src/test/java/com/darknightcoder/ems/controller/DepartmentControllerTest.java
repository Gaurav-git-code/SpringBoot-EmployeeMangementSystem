package com.darknightcoder.ems.controller;

import com.darknightcoder.ems.model.DepartmentDto;
import com.darknightcoder.ems.model.DepartmentEmployeesDto;
import com.darknightcoder.ems.model.DepartmentResponse;
import com.darknightcoder.ems.model.EmployeeDto;
import com.darknightcoder.ems.security.JwtUtils;
import com.darknightcoder.ems.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(value = DepartmentController.class)
@AutoConfigureMockMvc(addFilters = false)
class DepartmentControllerTest {

    @MockitoBean
    private DepartmentService departmentService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private JwtUtils jwtUtils;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private DepartmentDto departmentDto;

    @BeforeEach
    void setUp(){
        this.departmentDto = DepartmentDto.builder()
                .departmentId(1L)
                .departmentName("Research")
                .departmentType("B2B")
                .build();
    }

    //Junit test case for create department
    @Test
    @DisplayName("Test case : create department")
    public void givenDepartmentDto_whenCreateDepartment_thenReturnSavedDepartment() throws Exception {
        //given - precondition or setup
        BDDMockito
                .given(departmentService.createDepartment(ArgumentMatchers.any(DepartmentDto.class)))
                .willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        //when -action or behaviour which we are going to test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/department")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(departmentDto))
        );


        //then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.departmentName",
                                CoreMatchers.is(departmentDto.getDepartmentName())))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    //Junit test case for get all department
    @Test
    @DisplayName("Test case : Get all department")
    public void givenDepartmentList_whenGetAllDepartment_thenReturnListOfDepartment() throws Exception {
        //given - precondition or setup
        DepartmentResponse departmentResponse = DepartmentResponse.builder()
                        .departments(List.of(departmentDto))
                        .pageNo(0)
                        .pageSize(2)
                        .totalElement(1)
                        .totalPage(1)
                        .isLast(true)
                        .build();
        BDDMockito.given(
                departmentService
                        .getAllDepartment(
                                ArgumentMatchers.anyInt(),
                                ArgumentMatchers.anyInt(),
                                ArgumentMatchers.anyString(),
                                ArgumentMatchers.anyString()))
                .willReturn(departmentResponse);

        //when -action or behaviour which we are going to test
       ResultActions response = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/department"));

        //then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.departments[0].departmentName",CoreMatchers.is("Research")))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.departments[0].departmentType",CoreMatchers.is("B2B")))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.departments[0].departmentId",CoreMatchers.is(1)))
                .andExpect((MockMvcResultMatchers
                        .jsonPath("$.pageNo",CoreMatchers.is(0))))
                .andExpect((MockMvcResultMatchers
                        .jsonPath("$.pageSize",CoreMatchers.is(2))))
                .andExpect((MockMvcResultMatchers
                        .jsonPath("$.totalElement",CoreMatchers.is(1))))
                .andExpect((MockMvcResultMatchers
                        .jsonPath("$.totalPage",CoreMatchers.is(1))))
                .andExpect((MockMvcResultMatchers
                        .jsonPath("$.last",CoreMatchers.is(true))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //Junit test case for find department with id
    @Test
    @DisplayName("Test case : get department by Id")
    public void givenDepartment_whenGetDepartmentById_thenReturnDepartment() throws Exception {

        //when -action or behaviour which we are going to test
        BDDMockito.given(departmentService.getDepartmentById(1))
                .willReturn(departmentDto);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/department/{id}",departmentDto.getDepartmentId()));

        //then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.departmentId",CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.departmentName",CoreMatchers.is("Research")))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.departmentType",CoreMatchers.is("B2B")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //Junit test case for get all employee for department
    @Test
    @DisplayName("Test case : get all Employee for department")
    public void givenDepartmentAndEmployeeObject_whenGetAllEmployeesForDepartment_thenReturnDepartmentEmployees() throws Exception {
        //given - precondition or setup
        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(1)
                .firstName("Gaurav")
                .lastName("Suman")
                .email("gaurav@gmail.com")
                .departmentId(1L)
                .build();
        DepartmentEmployeesDto departmentEmployeesDto = DepartmentEmployeesDto.builder()
                .departmentId(1)
                .departmentName("Research")
                .departmentType("B2B")
                .listOfEmployee(List.of(employeeDto))
                .pageNo(0)
                .pageSize(5)
                .totalElement(1)
                .totalPages(1)
                .isLast(true)
                .build();
        //when -action or behaviour which we are going to test
        BDDMockito.given(departmentService
                        .getAllEmployeeForDepartment(ArgumentMatchers.eq(1L),
                                                        ArgumentMatchers.anyInt(),
                                                        ArgumentMatchers.anyInt(),
                                                        ArgumentMatchers.anyString(),
                                                        ArgumentMatchers.anyString()))
                .willReturn(departmentEmployeesDto);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/department/{id}/allEmployees",departmentDto.getDepartmentId()));
        //then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.departmentId",CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.departmentName",CoreMatchers.is("Research")))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.departmentType",CoreMatchers.is("B2B")))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.listOfEmployee[0].id",CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.listOfEmployee[0].firstName",CoreMatchers.is("Gaurav")))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.listOfEmployee[0].lastName",CoreMatchers.is("Suman")))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.listOfEmployee[0].email",CoreMatchers.is("gaurav@gmail.com")))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.listOfEmployee[0].departmentId",CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.pageNo",CoreMatchers.is(0)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.pageSize",CoreMatchers.is(5)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.totalElement",CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.totalPages",CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.last",CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    //Junit test case for update department
    @Test
    @DisplayName("Test case : update department")
    public void givenDepartment_whenUpdateDepartment_thenReturnUpdatedDepartment() throws Exception {
        //given - precondition or setup
        DepartmentDto updateDepartment = DepartmentDto.builder()
                .departmentId(1)
                .departmentName("Finance")
                .departmentType("B2C")
                .build();

        //when -action or behaviour which we are going to test
        BDDMockito.given(departmentService.updateDepartment(ArgumentMatchers.eq(1L),ArgumentMatchers.any(DepartmentDto.class)))
                .willAnswer(invocationOnMock -> invocationOnMock.getArgument(1));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/department/{id}",departmentDto.getDepartmentId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDepartment))
        );

        //then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.departmentId",CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.departmentName",CoreMatchers.is("Finance")))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.departmentType", CoreMatchers.is("B2C")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //Junit test case for delete department
    @Test
    @DisplayName("Test case : delete department")
    public void givenDepartment_whenDeleteDepartment_thenReturnDeleteDepartment() throws Exception {
        //when -action or behaviour which we are going to test
        BDDMockito.willDoNothing().given(departmentService).deleteDepartment(1);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/department/{id}",departmentDto.getDepartmentId()));

        //then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}