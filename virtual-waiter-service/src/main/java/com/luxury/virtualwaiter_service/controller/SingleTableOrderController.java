package com.luxury.virtualwaiter_service.controller;

import com.luxury.virtualwaiter_service.service.SingleTableOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vw/singleTableOrder")
public class SingleTableOrderController {

    private final SingleTableOrderService singleTableOrderService;

    @Autowired
    public SingleTableOrderController(SingleTableOrderService singleTableOrderService) {
        this.singleTableOrderService = singleTableOrderService;
    }
}
