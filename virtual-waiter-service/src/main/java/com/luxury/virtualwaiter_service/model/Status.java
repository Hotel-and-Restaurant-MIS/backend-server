package com.luxury.virtualwaiter_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;
    private String statusName;

    @OneToMany(mappedBy = "status")
    @JsonBackReference
    private List<SingleTableOrder> singleTableOrders;
}
