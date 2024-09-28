package com.luxury.reservation_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String roomTypeName;

    private Double pricePerDay;
    private String description;
    private Integer quantity;
    private Integer noOfPersons;

    @JsonIgnore
    @OneToMany(mappedBy = "roomType")
    private List<Reservation> reservations;

}
