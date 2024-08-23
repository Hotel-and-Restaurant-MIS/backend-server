package com.luxury.employee_service.service;

import com.luxury.employee_service.model.Employee;
import com.luxury.employee_service.model.EmployeeRole;
import com.luxury.employee_service.repository.EmployeeRepository;
import com.luxury.employee_service.rto.EmployeeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee addEmployee(EmployeeRecord employeeRecord) {
        try {
            var newEmployee = Employee.builder()
                    .name(employeeRecord.name())
                    .nic(employeeRecord.nic())
                    .phoneNumber(employeeRecord.phoneNumber())
                    .email(employeeRecord.email())
                    .employeeRole(
                            EmployeeRole.builder()
                                    .roleId(employeeRecord.roleId()). build()
                    ).build();

            return employeeRepository.save(newEmployee);
        }catch(Exception e) {
            throw new RuntimeException("Failed to add reservation", e);
        }
    }
}
