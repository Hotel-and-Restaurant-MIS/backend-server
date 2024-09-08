package com.luxury.reservation_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomTypeName;

    private Double pricePerDay;
    private String description;
    private Integer quantity;
    private Integer noOfPersons;

    @OneToMany(mappedBy = "roomType")
    private List<Reservation> reservations;

}
