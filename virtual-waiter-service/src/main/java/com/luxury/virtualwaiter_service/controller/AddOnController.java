package com.luxury.virtualwaiter_service.controller;

import com.luxury.virtualwaiter_service.service.AddOnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/addOn")
public class AddOnController {

    private final AddOnService addOnService;

    @Autowired
    public AddOnController(AddOnService addOnService) {
        this.addOnService = addOnService;
    }
}
