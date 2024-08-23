package com.luxury.virtualwaiter_service.service;

import com.luxury.virtualwaiter_service.repository.AddOnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddOnService {

    private final AddOnRepository addOnRepository;

    @Autowired
    public AddOnService(AddOnRepository addOnRepository) {
        this.addOnRepository = addOnRepository;
    }
}
