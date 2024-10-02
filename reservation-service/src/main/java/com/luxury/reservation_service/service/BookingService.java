package com.luxury.reservation_service.service;

import com.luxury.reservation_service.dto.BookingDTO;
import com.luxury.reservation_service.exception.StoredProcedureCallException;
import com.luxury.reservation_service.model.Booking;
import com.luxury.reservation_service.model.RoomCount;
import com.luxury.reservation_service.model.RoomType;
import com.luxury.reservation_service.repository.BookingRepository;
import com.luxury.reservation_service.repository.RoomCountByCategoryRepository;
import com.luxury.reservation_service.repository.RoomTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookingService {

    public static final Logger LOG = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final RoomCountByCategoryRepository roomCountByCategoryRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    public Double getRoomTypePrice(String roomTypeName) {
        RoomType roomType = roomTypeRepository.findRoomTypeByRoomTypeName(roomTypeName).orElse(null); // Change to String ID lookup
        return roomType != null ? roomType.getPricePerDay() : 0.0; // Return the price or 0 if not found
    }


    // Constructor for injecting BookingRepository and RoomCountByCategoryRepository dependencies
    @Autowired
    public BookingService(BookingRepository bookingRepository, RoomCountByCategoryRepository roomCountByCategoryRepository) {
        this.bookingRepository = bookingRepository;
        this.roomCountByCategoryRepository = roomCountByCategoryRepository;
    }

    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        // Fetch all bookings from the repository
        List<Booking> bookings = bookingRepository.findAll();

        // Map the list of Booking entities to BookingDTOs
        List<BookingDTO> bookingDTOs = bookings.stream().map(booking -> {
            // Fetch the room type price using the roomTypeName from Booking
            Double roomTypePrice = getRoomTypePrice(booking.getRoomTypeName());

            // Use the builder to create BookingDTO
            return BookingDTO.builder()
                    .bookingID(booking.getBookingId())
                    .roomTypeName(booking.getRoomTypeName())// Set to null as we're not using RoomType directly in BookingDTO
                    .checkinDate(booking.getCheckinDate())
                    .checkoutDate(booking.getCheckoutDate())
                    .roomQuantity(booking.getRoomQuantity())
                    .status("Completed") // Force status to "Booked"
                    .totalPrice(calculateTotalPrice(booking, roomTypePrice)) // Use the room type price for calculation
                    .customer(booking.getCustomer())
                    .build();
        }).collect(Collectors.toList());

        // Return the list of BookingDTOs with HTTP status 200 OK
        return new ResponseEntity<>(bookingDTOs, HttpStatus.OK);
    }




    private Double calculateTotalPrice(Booking booking, Double roomTypePrice) {
        // Calculate the number of days between check-in and check-out dates
        long dayCount = ChronoUnit.DAYS.between(booking.getCheckinDate(), booking.getCheckoutDate());
        return booking.getRoomQuantity() * roomTypePrice * dayCount;
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
