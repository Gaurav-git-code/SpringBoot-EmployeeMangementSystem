package com.darknightcoder.ems.controller;

import com.darknightcoder.ems.model.EmployeeDto;
import com.darknightcoder.ems.model.EmployeeResponse;
import com.darknightcoder.ems.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/employee")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(
            @Valid @RequestBody EmployeeDto employeeDto){
        return new ResponseEntity<>(employeeService.createEmployee(employeeDto),
                HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<EmployeeResponse> getAllEmployee(
            @RequestParam long departmentId,
            @RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "2") int pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = "firstName") String sortBy,
            @RequestParam(value = "sortDir", required = false, defaultValue = "ASC") String sortDir
    ){
        return new ResponseEntity<>(
                employeeService.getAllEmployee(departmentId,pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(
            @RequestParam(value = "departmentId") long departmentId,
            @PathVariable(value = "employeeId") long employeeId
    ){
        return new ResponseEntity<>(
                employeeService.getEmployeeById(departmentId, employeeId),HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{employeeId}")
    public ResponseEntity<EmployeeDto> updateEmployee(
            @PathVariable("employeeId") long employeeId,
            @Valid @RequestBody EmployeeDto employeeDto){
        return new ResponseEntity<>(employeeService.updateEmployee(employeeId,employeeDto),HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{employeeId}")
    public ResponseEntity<String> deleteEmployee(
            @RequestParam("departmentId") long departmentId,
            @PathVariable("employeeId") long employeeId){
        employeeService.deleteEmployee(departmentId, employeeId);
        return new ResponseEntity<>("Employee Deleted Successfully!",HttpStatus.OK);
    }
}
