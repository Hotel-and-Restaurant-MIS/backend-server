package com.luxury.virtualwaiter_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddOn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addOnId;

    private String addOnName;
    private Double addOnPrice;

    @ManyToOne
    @JoinColumn(name = "menu_item_id", nullable = false)
    @JsonBackReference // Prevent recursion from the other side
    private MenuItem menuItem;

    @OneToMany(mappedBy = "addOn", fetch = FetchType.EAGER)
    @JsonManagedReference // To manage the relationship
    private List<SelectedAddOn> selectedAddOns;

}
