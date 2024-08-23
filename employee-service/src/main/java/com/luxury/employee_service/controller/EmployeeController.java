package com.luxury.employee_service.controller;

import com.luxury.employee_service.model.Employee;
import com.luxury.employee_service.rto.EmployeeRecord;
import com.luxury.employee_service.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping("/add")
    public Employee addEmployee(@RequestBody EmployeeRecord employeeRecord) {
        return employeeService.addEmployee(employeeRecord);
    }
}
