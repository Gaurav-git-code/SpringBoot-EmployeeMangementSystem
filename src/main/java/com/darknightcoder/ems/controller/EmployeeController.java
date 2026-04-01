package com.darknightcoder.ems.controller;

import com.darknightcoder.ems.model.EmployeeDto;
import com.darknightcoder.ems.model.EmployeeResponse;
import com.darknightcoder.ems.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/department")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;


    @PostMapping(value = "/{departmentId}/employee")
    public ResponseEntity<EmployeeDto> createEmployee(@PathVariable long departmentId,@RequestBody EmployeeDto employeeDto){
        return new ResponseEntity<>(employeeService.createEmployee(employeeDto,departmentId),
                HttpStatus.CREATED);
    }



    @GetMapping(value = "/{departmentId}/employees")
    public ResponseEntity<EmployeeResponse> getAllEmployee(
            @PathVariable long departmentId,
            @RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "2") int pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = "firstName") String sortBy,
            @RequestParam(value = "sortDir", required = false, defaultValue = "ASC") String sortDir
    ){
        return new ResponseEntity<>(
                employeeService.getAllEmployee(departmentId,pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
    }



    @GetMapping(value = "/{departmentId}/employee/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(
            @PathVariable(value = "departmentId") long departmentId,
            @PathVariable(value = "employeeId") long employeeId
    ){
        return new ResponseEntity<>(
                employeeService.getEmployeeById(departmentId, employeeId),HttpStatus.OK);
    }



    @PutMapping(value = "/{departmentId}/employee/{employeeId}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("departmentId") long departmentId,
                                                      @PathVariable("employeeId") long employeeId,
                                                      @RequestBody EmployeeDto employeeDto){
        return new ResponseEntity<>(employeeService.updateEmployee(departmentId, employeeId,employeeDto),HttpStatus.OK);
    }



    @DeleteMapping(value = "/{departmentId}/employee/{employeeId}")
    public ResponseEntity<String> deleteEmployee(
            @PathVariable("departmentId") long departmentId,
            @PathVariable("employeeId") long employeeId){
        employeeService.deleteEmployee(departmentId, employeeId);
        return new ResponseEntity<>("Employee Deleted Successfully!",HttpStatus.OK);
    }
}
