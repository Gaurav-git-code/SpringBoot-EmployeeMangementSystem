package com.darknightcoder.ems.controller;

import com.darknightcoder.ems.model.DepartmentDto;
import com.darknightcoder.ems.model.DepartmentEmployeesDto;
import com.darknightcoder.ems.model.DepartmentResponse;
import com.darknightcoder.ems.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/department")
@AllArgsConstructor
public class DepartmentController {
    private DepartmentService departmentService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(
            @Valid @RequestBody DepartmentDto departmentDto){
        return new ResponseEntity<>(departmentService.createDepartment(departmentDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<DepartmentResponse> getAllDepartment(
            @RequestParam(value = "pageNo",defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "2", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "departmentName", required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "ASC",required = false) String sortDir
    ){
        DepartmentResponse response = departmentService.getAllDepartment(pageNo, pageSize,sortBy,sortDir);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/{departmentId}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable long departmentId){
        return new ResponseEntity<>(departmentService.getDepartmentById(departmentId),HttpStatus.OK);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/{departmentId}/allEmployees")
    public ResponseEntity<DepartmentEmployeesDto> getAllEmployeesForDepartment(
            @PathVariable long departmentId,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5",required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "departmentName", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "ASC",required = false) String sortDir
    ){
        return new ResponseEntity<>(
                departmentService.getAllEmployeeForDepartment(departmentId,pageNo,pageSize,sortBy,sortDir),
                HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{departmentId}")
    public ResponseEntity<DepartmentDto> updateDepartment(
            @PathVariable long departmentId,
            @Valid @RequestBody DepartmentDto departmentDto){
        return new ResponseEntity<>(departmentService.updateDepartment(departmentId, departmentDto),HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{departmentId}")
    public ResponseEntity<String> deleteDepartment(@PathVariable long departmentId){
        departmentService.deleteDepartment(departmentId);
        return new ResponseEntity<>("Department deleted successfully !",HttpStatus.OK);
    }
}
