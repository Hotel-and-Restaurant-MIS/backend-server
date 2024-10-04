package com.luxury.virtualwaiter_service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    private String menuItemName;
    private String description;
    private String imageUrl;
    private Double menuItemPrice;

    // Many MenuItems belong to one Category
    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    private Category category;

    // Many MenuItems have many Tags

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)  // LAZY fetching to avoid performance issues
    @JoinTable(
            name = "menu_item_tag",
            joinColumns = @JoinColumn(name = "menu_item_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @JsonManagedReference
    private List<Tag> tags;

    // One MenuItem can have multiple AddOns
    @OneToMany(mappedBy = "menuItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // LAZY fetching for better performance
    @JsonManagedReference  // Prevents recursion when serializing to JSON
    private List<AddOn> addOns;
}
