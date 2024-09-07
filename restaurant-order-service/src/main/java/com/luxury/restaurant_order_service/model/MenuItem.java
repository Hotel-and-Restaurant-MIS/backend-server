package com.luxury.restaurant_order_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuItemId;

    private String manuItemName;
    private String imgUrl;
    private String description;
    private Double menuItemPrice;

    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;

    @OneToMany
    @JoinColumn(name = "menu_item_addon",nullable = false)
    private List<AddOn> addOns;

    @OneToMany
    @JoinColumn(name = "menu_item_tag", nullable = false)
    private List<Tag> tags;

}
