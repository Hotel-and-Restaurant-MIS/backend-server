package com.luxury.restaurant_order_service.controller;

import com.luxury.restaurant_order_service.dto.OrderDTO;
import com.luxury.restaurant_order_service.model.Order;
import com.luxury.restaurant_order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    @PostMapping("/add")
    public Order addOrder(@RequestBody OrderDTO orderDTO){
        return orderService.addOrder(orderDTO);
    }
}
