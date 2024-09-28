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

    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    private Category category;

    @ManyToMany(fetch = FetchType.EAGER) // Eager fetching to ensure tags are loaded
    @JoinTable(
            name = "menu_item_tag",
            joinColumns = @JoinColumn(name = "menu_item_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )

    @JsonManagedReference
    private List<Tag> tags;
}
