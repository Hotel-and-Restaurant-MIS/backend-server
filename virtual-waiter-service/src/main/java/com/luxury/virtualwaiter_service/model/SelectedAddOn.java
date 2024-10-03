package com.luxury.virtualwaiter_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelectedAddOn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long selectedAddOnId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "add_on_id")
    private AddOn addOn;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;
}
