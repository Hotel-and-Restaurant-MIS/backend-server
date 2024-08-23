package com.luxury.employee_service.controller;

import com.luxury.employee_service.model.EmployeeRole;
import com.luxury.employee_service.service.EmployeeRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee/role")
public class EmployeeRoleController {

    private final EmployeeRoleService employeeRoleService;

    @Autowired
    public EmployeeRoleController(EmployeeRoleService employeeRoleService) {
        this.employeeRoleService = employeeRoleService;
    }

    @GetMapping("/all")
    public List<EmployeeRole> getAllEmployeeRole() {
        return employeeRoleService.getAllEmployeeRoles();
    }
}
