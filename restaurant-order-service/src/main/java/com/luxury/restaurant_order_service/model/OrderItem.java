package com.luxury.restaurant_order_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @OneToOne
//    @JoinColumn(name = "menu_item_id",nullable = false)
    private MenuItem menuItem;

    private Integer quantity;
    private Double totalPrice;
    private String specialNote;
    private Date dateTime;

    @OneToMany
//    @JoinColumn(name="selected_addon",nullable = false)
    private List<AddOn> selectedAddOns;

}
