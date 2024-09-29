package com.luxury.virtualwaiter_service.service;

import com.luxury.virtualwaiter_service.model.MenuItem;
import com.luxury.virtualwaiter_service.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }


    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }
}
