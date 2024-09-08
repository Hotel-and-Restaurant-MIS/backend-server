package com.luxury.restaurant_order_service.repository;

import com.luxury.restaurant_order_service.model.Order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
