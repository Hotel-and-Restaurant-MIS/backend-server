package com.luxury.reservation_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToOne(mappedBy = "customer")
    private Reservation reservation;
}