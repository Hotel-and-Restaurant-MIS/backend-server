package com.luxury.virtualwaiter_service.repository;

import com.luxury.virtualwaiter_service.model.SingleTableOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SingleTableOrderRepository extends JpaRepository<SingleTableOrder, Long> {
 }
