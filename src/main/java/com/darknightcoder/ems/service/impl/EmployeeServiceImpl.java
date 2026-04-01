package com.darknightcoder.ems.service.impl;

import static com.darknightcoder.ems.mapper.EmployeeMapper.*;

import com.darknightcoder.ems.entity.Department;
import com.darknightcoder.ems.entity.Employee;
import com.darknightcoder.ems.exception.ResourceNotFoundException;
import com.darknightcoder.ems.mapper.EmployeeMapper;
import com.darknightcoder.ems.model.EmployeeDto;
import com.darknightcoder.ems.model.EmployeeResponse;
import com.darknightcoder.ems.repository.DepartmentRepository;
import com.darknightcoder.ems.repository.EmployeeRepository;
import com.darknightcoder.ems.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;


    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto, long departmentId) {
        Department department = departmentRepository.findById(departmentId).orElse(null);
        Employee savedEmployee = employeeRepository.save(mapToEmployee(employeeDto, department));
        return maptoEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(long departmentId, long employeeId) {
        departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department","Id",departmentId));
        Employee fetchedEmployee =employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee","Id",employeeId));
        return maptoEmployeeDto(fetchedEmployee);
    }

    @Override
    public EmployeeResponse getAllEmployee(long departmentId, int pageNo, int pageSize, String sortBy, String sortDir) {
    Department department = departmentRepository.findById(departmentId)
        .orElseThrow(() -> new ResourceNotFoundException("Department","Id",departmentId));
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Employee> page = employeeRepository.findAllByDepartment(department,pageable);
        List<Employee> employeeList = page.getContent();
        EmployeeResponse employeeResponse = new EmployeeResponse();


        List<EmployeeDto> employeeDtoList = employeeList.stream().map(EmployeeMapper::maptoEmployeeDto)
                                        .collect(Collectors.toList());
        employeeResponse.setContent(employeeDtoList);
        employeeResponse.setPageNo(page.getNumber());
        employeeResponse.setPageSize(page.getSize());
        employeeResponse.setTotalPage(page.getTotalPages());
        employeeResponse.setTotalElement((int) page.getTotalElements());
        employeeResponse.setLastPage(page.isLast());
        return employeeResponse;
    }

    @Override
    public EmployeeDto updateEmployee(long departmentId, long employeeId,EmployeeDto employeeDto) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department","Id",departmentId));
        Employee savedEmployee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee","Id",employeeId));
        savedEmployee.setFirstName(employeeDto.getFirstName());
        savedEmployee.setLastName(employeeDto.getLastName());
        savedEmployee.setEmail(employeeDto.getEmail());
        Employee updatedEmployee = employeeRepository.save(savedEmployee);
        return maptoEmployeeDto(updatedEmployee);
    }

    @Override
    public void deleteEmployee(long departmentId, long employeeId) {
        departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department","Id",departmentId));
        employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee","Id",employeeId));
        employeeRepository.deleteById(employeeId);
    }
}
