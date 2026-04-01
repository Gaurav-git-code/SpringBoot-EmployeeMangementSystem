package com.darknightcoder.ems.controller;

import com.darknightcoder.ems.model.DepartmentDto;
import com.darknightcoder.ems.model.DepartmentEmployeesDto;
import com.darknightcoder.ems.model.DepartmentResponse;
import com.darknightcoder.ems.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/department")
@AllArgsConstructor
public class DepartmentController {
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto){
        return new ResponseEntity<>(departmentService.createDepartment(departmentDto), HttpStatus.CREATED);
    }


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



    @GetMapping(value = "/{departmentId}")
    public ResponseEntity<DepartmentDto> getDepartmentById(long departmentId){
        return new ResponseEntity<>(departmentService.getDepartmentById(departmentId),HttpStatus.OK);
    }



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



    @PutMapping(value = "/{departmentId}")
    public ResponseEntity<DepartmentDto> updateDepartment(
            @PathVariable long departmentId, @RequestBody DepartmentDto departmentDto){
        return new ResponseEntity<>(departmentService.updateDepartment(departmentId, departmentDto),HttpStatus.OK);
    }



    @DeleteMapping(value = "/{departmentId}")
    public ResponseEntity<String> deleteDepartment(@PathVariable long departmentId){
        departmentService.deleteDepartment(departmentId);
        return new ResponseEntity<>("Department deleted successfully !",HttpStatus.OK);
    }
}
