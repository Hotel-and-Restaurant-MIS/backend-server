package com.luxury.reservation_service.controller;

import com.luxury.reservation_service.dto.RoomTypeDTO;
import com.luxury.reservation_service.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reservations/roomtype")
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    // Constructor for injecting RoomTypeService dependency
    @Autowired
    public RoomTypeController(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    // Endpoint to get all room types and their prices
    @GetMapping("/price")
    public ResponseEntity<List<RoomTypeDTO>> getAllRoomTypes() {
        // Retrieve room types from the service and return them with an OK status
        List<RoomTypeDTO> roomTypes = roomTypeService.getAllRoomTypePrice();
        return ResponseEntity.ok(roomTypes);
    }
}
