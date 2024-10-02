package com.luxury.reservation_service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private String roomTypeName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkinDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkoutDate;
    private Integer roomQuantity;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ReservedRooms> reservedRooms; // List to hold reserved rooms

}
