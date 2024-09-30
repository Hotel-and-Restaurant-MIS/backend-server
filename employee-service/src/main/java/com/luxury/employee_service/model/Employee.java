package com.luxury.employee_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private EmployeeRole employeeRole;
}
