package com.luxury.employee_service.rto;

public record EmployeeRecord(
        String name,
        String email,
        String nic,
        String phoneNumber,
        Long roleId
        ) {
  }
