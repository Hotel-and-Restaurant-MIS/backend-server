package com.luxury.restaurant_order_service.service;

import com.luxury.restaurant_order_service.dto.OrderDTO;
import com.luxury.restaurant_order_service.enums.OrderStatus;
import com.luxury.restaurant_order_service.model.Order;
import com.luxury.restaurant_order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }


    public Order addOrder(OrderDTO orderDTO) {
        try {
            var newOrder = Order.builder()
                    .orderStatus(OrderStatus.PENDING)
                    .orderTotal(orderDTO.orderTotal())
                    .orderItems(orderDTO.orderItemList())
                    .tableId(orderDTO.tableId())
                    .build();
            return orderRepository.save(newOrder);
        }catch (Exception e){
            throw new RuntimeException("Failed to add new order");
        }
    }
}
