package com.luxury.reservation_service.controller;

import com.luxury.reservation_service.dto.BookingDTO;
import com.luxury.reservation_service.dto.BookingRequestDTO;
import com.luxury.reservation_service.dto.RoomCountDTO;
import com.luxury.reservation_service.model.RoomCount;
import com.luxury.reservation_service.service.BookingService;
import com.luxury.reservation_service.service.ReservationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService, ReservationService reservationService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        //Get all bookings
        return bookingService.getAllBookings();
    }

    @GetMapping("/totalAvailableRoomCount")
    public ResponseEntity<List<RoomCountDTO>> getAvailableRoomCount(@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from, @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return bookingService.getAvailableRoomCount(from, to);
    }

    @PostMapping("/add")
    public ResponseEntity<BookingDTO> addBooking(@RequestBody BookingRequestDTO bookingRequestDTO) {
        return bookingService.addBooking(bookingRequestDTO);
    }



}
