package com.luxury.virtualwaiter_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@IdClass(SingleTableOrderPK.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SingleTableOrder {
    @Id
    private Long orderId;
    @Id
    private Integer tableId;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @OneToMany
    private List<OrderItem> orderItemList;
}
