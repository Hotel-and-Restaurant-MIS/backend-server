package com.luxury.employee_service.service;

import com.luxury.employee_service.dto.EmployeeDto;
import com.luxury.employee_service.model.Employee;
import com.luxury.employee_service.model.EmployeeRole;
import com.luxury.employee_service.repository.EmployeeRepository;
import com.luxury.employee_service.repository.EmployeeRoleRepository;
import com.luxury.employee_service.rto.EmployeeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeRoleRepository employeeRoleRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, EmployeeRoleRepository employeeRoleRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeRoleRepository = employeeRoleRepository;
    }

    public List<EmployeeDto> getAllEmployees() {
        // Retrieve all Employee entities from the repository
        List<Employee> employees = employeeRepository.findAll();

        // Map each Employee entity to an EmployeeDto and collect them into a list
        return employees.stream()
                .map(employee -> new EmployeeDto(
                        employee.getName(),
                        employee.getEmail(),
                        employee.getNic(),
                        employee.getPhoneNumber(),
                        employee.getEmployeeRole().getRoleType(),
                        employee.getEmployeeId())
                ) // Mapping Employee to EmployeeDto
                .collect(Collectors.toList()); // Collect to List<EmployeeDto>
    }

    public EmployeeDto addEmployee(EmployeeRecord employeeRecord) {
        try {
            var newEmployee = employeeRepository.save(
                    Employee.builder()
                            .name(employeeRecord.name())
                            .nic(employeeRecord.nic())
                            .phoneNumber(employeeRecord.phoneNumber())
                            .email(employeeRecord.email())
                            .employeeRole(
                                    EmployeeRole.builder()
                                            .roleId(employeeRecord.roleId()).build()
                            ).build()
            );

            var role = employeeRoleRepository.findById(newEmployee.getEmployeeRole().getRoleId().intValue()).orElse(null);

            assert role != null;
            return EmployeeDto.builder()
                    .name(newEmployee.getName())
                    .email(newEmployee.getEmail())
                    .nic(newEmployee.getNic())
                    .phoneNumber(newEmployee.getPhoneNumber())
                    .role(role.getRoleType())
                    .employeeId(newEmployee.getEmployeeId())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Failed to add reservation", e);
        }
    }
}
