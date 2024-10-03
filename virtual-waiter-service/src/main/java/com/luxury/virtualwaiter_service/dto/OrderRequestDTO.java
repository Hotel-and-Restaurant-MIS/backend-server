package com.luxury.virtualwaiter_service.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDTO {
    private List<OrderItemDTO> orderItemList;
    private String tableId;
    private String orderTotal;
}
