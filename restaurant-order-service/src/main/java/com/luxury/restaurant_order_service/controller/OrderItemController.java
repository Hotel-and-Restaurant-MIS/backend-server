package com.luxury.restaurant_order_service.controller;

import com.luxury.restaurant_order_service.model.OrderItem;
import com.luxury.restaurant_order_service.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orderItem")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService){
        this.orderItemService = orderItemService;
    }

    @GetMapping("/getAll")
    public List<OrderItem> getAllOrders(){
        return orderItemService.getAllOrderItems();
    }

//    @PostMapping("/addOrder")
}
