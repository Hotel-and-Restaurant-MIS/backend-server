package com.luxury.reservation_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String name;
    private String nic;
    private String email;
    private String phone;

    @OneToOne(mappedBy = "customer")
    private Reservation reservation;
}