package com.darknightcoder.ems.service.impl;

import static com.darknightcoder.ems.mapper.DepartmentMapper.*;

import com.darknightcoder.ems.entity.Department;
import com.darknightcoder.ems.entity.Employee;
import com.darknightcoder.ems.exception.DeleteResourceException;
import com.darknightcoder.ems.exception.ResourceNotFoundException;
import com.darknightcoder.ems.mapper.DepartmentMapper;
import com.darknightcoder.ems.mapper.EmployeeMapper;
import com.darknightcoder.ems.model.DepartmentDto;
import com.darknightcoder.ems.model.DepartmentEmployeesDto;
import com.darknightcoder.ems.model.DepartmentResponse;
import com.darknightcoder.ems.model.EmployeeDto;
import com.darknightcoder.ems.repository.DepartmentRepository;
import com.darknightcoder.ems.repository.EmployeeRepository;
import com.darknightcoder.ems.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;
    private EmployeeRepository employeeRepository;

    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        return mapToDepartmentDto(departmentRepository.save(mapToDepartment(departmentDto)));
    }

    @Override
    public DepartmentResponse getAllDepartment(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);

        Page<Department> page = departmentRepository.findAll(pageable);
        List<Department> listOfDepartment = page.getContent();

        List<DepartmentDto> listDepartment = listOfDepartment.stream().map((DepartmentMapper::mapToDepartmentDto))
                .collect(Collectors.toList());
        return new DepartmentResponse(
                listDepartment,
                page.getNumber(),
                page.getSize(),
                (int) page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    @Override
    public DepartmentDto getDepartmentById(long id) {
        return mapToDepartmentDto(
                departmentRepository.findById(id).orElseThrow(
                        ()->new ResourceNotFoundException("Department","Id",id)));
    }

    @Override
    public DepartmentEmployeesDto getAllEmployeeForDepartment(long departmentId, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);


        Department department = departmentRepository.findById(departmentId).orElseThrow(
                ()->new ResourceNotFoundException("Department","Id",departmentId));

        Page<Employee> page = employeeRepository.findAllByDepartment(department,pageable);
        List<EmployeeDto> employeeDtoList = page
                .getContent()
                .stream()
                .map(EmployeeMapper::maptoEmployeeDto)
                .collect(Collectors.toList());


        return new DepartmentEmployeesDto(
                departmentId,
                department.getDepartmentName(),
                department.getDepartmentType(),
                employeeDtoList,
                page.getNumber(),
                page.getSize(),
                (int) page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    @Override
    public DepartmentDto updateDepartment(long id, DepartmentDto departmentDto) {
        Department savedDepartment = departmentRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Department","Id",id));
        savedDepartment.setDepartmentType(departmentDto.getDepartmentType());
        savedDepartment.setDepartmentName(departmentDto.getDepartmentName());
        return mapToDepartmentDto(departmentRepository.save(savedDepartment));
    }

    @Override
    public void deleteDepartment(long departmentId) {
        Department departmentToDelete =departmentRepository.findById(departmentId)
                        .orElseThrow(() -> new ResourceNotFoundException("Department","Id",departmentId));

        int count = employeeRepository.countByDepartment(departmentToDelete);
        if (count > 0){
           throw new DeleteResourceException("Department",departmentId);
        }

        departmentRepository.deleteById(departmentId);

    }
}
