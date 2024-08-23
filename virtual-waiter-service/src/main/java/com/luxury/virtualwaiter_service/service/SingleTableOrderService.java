package com.luxury.virtualwaiter_service.service;

import com.luxury.virtualwaiter_service.repository.SingleTableOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleTableOrderService {
    private final SingleTableOrderRepository singleTableOrderRepository;

    @Autowired
    public SingleTableOrderService(SingleTableOrderRepository singleTableOrderRepository) {
        this.singleTableOrderRepository = singleTableOrderRepository;
    }
}
