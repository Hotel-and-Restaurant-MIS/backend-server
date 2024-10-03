package com.luxury.virtualwaiter_service.controller;

import com.luxury.virtualwaiter_service.dto.OrderRequestDTO;
import com.luxury.virtualwaiter_service.model.SingleTableOrder;
import com.luxury.virtualwaiter_service.service.SingleTableOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vw/singleTableOrder")
public class SingleTableOrderController {

    private final SingleTableOrderService singleTableOrderService;

    @Autowired
    public SingleTableOrderController(SingleTableOrderService singleTableOrderService) {
        this.singleTableOrderService = singleTableOrderService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<SingleTableOrder>> getAll() {
        return singleTableOrderService.getAllSingleTableOrders();
    }

    @PostMapping("/add")
    public ResponseEntity<SingleTableOrder> addSingleTableOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return singleTableOrderService.addSingleTableOrder(orderRequestDTO);
    }
}
