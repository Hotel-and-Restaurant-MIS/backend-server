package com.luxury.reservation_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {
    @Id
    private Long bookingID;

    private String roomTypeName;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private Integer roomQuantity;

    @OneToOne
    @JoinColumn(name= "customer_id", nullable = false)
    private Customer customer;

}
