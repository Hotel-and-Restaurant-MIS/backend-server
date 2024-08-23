package com.luxury.employee_service.service;

import com.luxury.employee_service.model.EmployeeRole;
import com.luxury.employee_service.repository.EmployeeRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeRoleService {

    private final EmployeeRoleRepository employeeRoleRepository;

    @Autowired
    public EmployeeRoleService(EmployeeRoleRepository employeeRoleRepository) {
        this.employeeRoleRepository = employeeRoleRepository;
    }

    public List<EmployeeRole> getAllEmployeeRoles() {
        return employeeRoleRepository.findAll();
    }

}
