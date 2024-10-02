package com.luxury.employee_service.dto;

import lombok.Builder;

@Builder
public record EmployeeDto(
        String name,
        String nic,
        String email,
        String phoneNumber,
        String role,
        Long employeeId
) {
}
//