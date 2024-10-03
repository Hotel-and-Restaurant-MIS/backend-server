package com.luxury.virtualwaiter_service.dto;

import com.luxury.virtualwaiter_service.model.AddOn;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDTO {
    private String menuItemId;
    private String specialNote;
    private String totalPrice;
    private String quantity;
    private List<String> addonList;
}
