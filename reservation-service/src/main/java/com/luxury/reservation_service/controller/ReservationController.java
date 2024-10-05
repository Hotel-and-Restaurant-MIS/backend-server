package com.luxury.reservation_service.controller;

import com.luxury.reservation_service.dto.ReservationDTO;
import com.luxury.reservation_service.model.Reservation;
import com.luxury.reservation_service.dto.ReservationRequest;
import com.luxury.reservation_service.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeReservation(@RequestParam Long reservationId) {
        return reservationService.removeReservation(reservationId);
    }


}
