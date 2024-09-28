package com.luxury.restaurant_order_service.dto;

import com.luxury.restaurant_order_service.enums.OrderStatus;
import com.luxury.restaurant_order_service.model.OrderItem;

import java.util.List;

public record OrderDTO(
        Double orderTotal,
        OrderStatus orderStatus,
        List<OrderItem> orderItemList,
        Integer tableId
) { }
