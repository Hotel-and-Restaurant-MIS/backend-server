package com.luxury.reservation_service.controller;

import com.luxury.reservation_service.service.RoomsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rooms")
public class RoomsController {

    private final RoomsService roomsService;

    public RoomsController(RoomsService roomsService) {
        this.roomsService = roomsService;
    }

    // Endpoint to get available rooms
    @GetMapping("/available")
    public ResponseEntity<Map<String, List<String>>> getAvailableRooms(
            @RequestParam String roomType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam int noOfRooms) {

        // Fetch available rooms using the service
        List<String> availableRooms = roomsService.getAvailableRooms(roomType, checkInDate, checkOutDate, noOfRooms);

        // Prepare the response
        Map<String, List<String>> response = new HashMap<>();
        response.put("roomList", availableRooms);

        // Return the available room list
        return ResponseEntity.ok(response);
    }
}
