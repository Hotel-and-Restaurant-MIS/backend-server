package com.luxury.reservation_service.service;

import com.fasterxml.jackson.core.ErrorReportConfiguration;
import com.luxury.reservation_service.dto.BookingDTO;
import com.luxury.reservation_service.dto.BookingRequestDTO;
import com.luxury.reservation_service.exception.StoredProcedureCallException;
import com.luxury.reservation_service.model.*;
import com.luxury.reservation_service.repository.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookingService {

    public static final Logger LOG = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    private final BookingRepository bookingRepository;
    @Autowired
    private final RoomCountByCategoryRepository roomCountByCategoryRepository;
    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private RoomsRepository roomsRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private final ReservedRoomsRepository reservedRoomsRepository;

    public Double getRoomTypePrice(String roomTypeName) {
        RoomType roomType = roomTypeRepository.findRoomTypeByRoomTypeName(roomTypeName).orElse(null); // Change to String ID lookup
        return roomType != null ? roomType.getPricePerDay() : 0.0; // Return the price or 0 if not found
    }


    // Constructor for injecting BookingRepository and RoomCountByCategoryRepository dependencies
    @Autowired
    public BookingService(BookingRepository bookingRepository, RoomCountByCategoryRepository roomCountByCategoryRepository, CustomerRepository customerRepository, ReservedRoomsRepository reservedRoomsRepository) {
        this.bookingRepository = bookingRepository;
        this.roomCountByCategoryRepository = roomCountByCategoryRepository;
        this.customerRepository = customerRepository;
        this.reservedRoomsRepository = reservedRoomsRepository;
    }

    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        // Fetch all bookings from the repository
        List<Booking> bookings = bookingRepository.findAll();

        // Map the list of Booking entities to BookingDTOs
        List<BookingDTO> bookingDTOs = bookings.stream().map(booking -> {
            // Fetch the room type price using the roomTypeName from Booking
            Double roomTypePrice = getRoomTypePrice(booking.getRoomTypeName());

            // Fetch reserved room numbers for this booking
            List<String> reservedRoomNumbers = booking.getReservedRooms().stream()
                    .map(reservedRoom -> String.valueOf(reservedRoom.getRooms().getRoomId()))
                    .collect(Collectors.toList());

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
                    .reservedRoomsNumbers(reservedRoomNumbers)
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

    @Transactional(Transactional.TxType.REQUIRED)
    public ResponseEntity<BookingDTO> addBooking(BookingRequestDTO bookingRequestDTO) {
        Customer customer;

        // Create a new Customer entity using the builder pattern
        customer = Customer.builder()
                .name(bookingRequestDTO.getName())
                .email(bookingRequestDTO.getEmail())
                .phone(bookingRequestDTO.getPhoneNumber())
                .nic(bookingRequestDTO.getNic())
                .build();

        // Save the new customer
        customerRepository.save(customer);

        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setRoomTypeName(bookingRequestDTO.getRoomType());

        // Convert check-in and check-out dates from String to LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkInDate = LocalDate.parse(bookingRequestDTO.getCheckInDate(), formatter);
        LocalDate checkOutDate = LocalDate.parse(bookingRequestDTO.getCheckOutDate(), formatter);
        booking.setCheckinDate(checkInDate);
        booking.setCheckoutDate(checkOutDate);

        // Convert number of rooms from String to Integer
        Integer roomQuantity = Integer.parseInt(bookingRequestDTO.getNoOfRooms());
        booking.setRoomQuantity(roomQuantity);

        // Save the booking and get the saved Booking entity
        Booking savedBooking = bookingRepository.save(booking);

        // Create and save reserved rooms
        List<String> reservedRoomNumbers = bookingRequestDTO.getRooms();
        for (String roomId : reservedRoomNumbers) {
            Rooms room = roomsRepository.findById(Long.valueOf(roomId))
                    .orElseThrow(() -> new RuntimeException("Room not found for ID: " + roomId));

            ReservedRooms reservedRoom = ReservedRooms.builder()
                    .booking(savedBooking) // Associate with the saved booking
                    .rooms(room) // Set the associated room
                    .build();

            // Save the reserved room
            reservedRoomsRepository.save(reservedRoom);
        }

        // Create the BookingDTO to return
        BookingDTO bookingDTO = BookingDTO.builder()
                .bookingID(savedBooking.getBookingId())
                .roomTypeName(savedBooking.getRoomTypeName())
                .checkinDate(savedBooking.getCheckinDate())
                .checkoutDate(savedBooking.getCheckoutDate())
                .roomQuantity(savedBooking.getRoomQuantity())
                .status("Completed")
                .totalPrice(Double.parseDouble(bookingRequestDTO.getTotalPrice())) // Convert String to Double
                .customer(customer)
                .reservedRoomsNumbers(reservedRoomNumbers)
                .build();

        // Return the BookingDTO with HTTP status 201 Created
        return new ResponseEntity<>(bookingDTO, HttpStatus.CREATED);
    }
}