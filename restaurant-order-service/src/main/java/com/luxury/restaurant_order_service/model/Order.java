package com.luxury.restaurant_order_service.model;

import com.luxury.restaurant_order_service.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name="`Order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Double orderTotal;

    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;

    @OneToMany
    private List<OrderItem> orderItems;

    private Integer tableId;
}
