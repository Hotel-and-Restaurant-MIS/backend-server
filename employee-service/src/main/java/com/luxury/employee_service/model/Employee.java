package com.luxury.employee_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long EmployeeId;

    private String name;
    private String email;
    private String phoneNumber;
    private String nic;

    @ManyToOne
    @JoinColumn(name="role_id", nullable=false)
    private EmployeeRole employeeRole;
}
