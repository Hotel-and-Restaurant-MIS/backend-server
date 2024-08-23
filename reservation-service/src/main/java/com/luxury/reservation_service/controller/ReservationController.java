package com.luxury.reservation_service.controller;

import com.luxury.reservation_service.model.Reservation;
import com.luxury.reservation_service.dto.ReservationRequest;
import com.luxury.reservation_service.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/add")
    public Reservation addReservation(@RequestBody ReservationRequest reservationRequest) {
        return reservationService.addReservation(reservationRequest);
    }

    @GetMapping("/all")
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    }
