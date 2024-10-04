package com.luxury.virtualwaiter_service.repository;

import com.luxury.virtualwaiter_service.model.OrderItem;
import com.luxury.virtualwaiter_service.model.SingleTableOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllBySingleTableOrder(SingleTableOrder singleTableOrder);
}
