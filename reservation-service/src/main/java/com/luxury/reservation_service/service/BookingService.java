package com.luxury.reservation_service.service;

import com.luxury.reservation_service.exception.StoredProcedureCallException;
import com.luxury.reservation_service.model.Booking;
import com.luxury.reservation_service.model.RoomCount;
import com.luxury.reservation_service.model.RoomType;
import com.luxury.reservation_service.repository.BookingRepository;
import com.luxury.reservation_service.repository.RoomCountByCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class BookingService {

    public static final Logger LOG = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final RoomCountByCategoryRepository roomCountByCategoryRepository;

    // Constructor for injecting BookingRepository and RoomCountByCategoryRepository dependencies
    @Autowired
    public BookingService(BookingRepository bookingRepository, RoomCountByCategoryRepository roomCountByCategoryRepository) {
        this.bookingRepository = bookingRepository;
        this.roomCountByCategoryRepository = roomCountByCategoryRepository;
    }

    // Method to retrieve all bookings
    public ResponseEntity<List<Booking>> getAllBookings() {
        // Fetch all bookings from the repository and return with HTTP status 200 OK
        return new ResponseEntity<>(bookingRepository.findAll(), HttpStatus.OK);
    }

    // Method to get the available room count based on check-in and check-out dates
    public ResponseEntity<List<RoomCount>> getAvailableRoomCount(LocalDate checkinDate, LocalDate checkoutDate) {
        try {
            // Call the repository to execute the stored procedure and get room count data
            Map<String, Object> data = roomCountByCategoryRepository.getAvailableRoomCount(checkinDate, checkoutDate);
            // Extract results from the stored procedure's result set
            List<Map<String, Object>> results = (List<Map<String, Object>>) data.get("#result-set-1");
            // Map the results to RoomCount objects
            List<RoomCount> roomCounts = results.stream().map(result -> new RoomCount((String) result.get("room_type_name"), (Integer) result.get("available_count"))).toList();
            // Return the room counts with HTTP status 200 OK
            return new ResponseEntity<>(roomCounts, HttpStatus.OK);
        } catch (StoredProcedureCallException e) {
            // Log the error and return HTTP status 500 Internal Server Error if the stored procedure call fails
            LOG.error("Stored Procedure Call Error in getAvailableRoomCount", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
