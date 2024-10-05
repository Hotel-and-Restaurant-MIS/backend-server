package com.luxury.reservation_service.service;

import com.luxury.reservation_service.dto.BookingDTO;
import com.luxury.reservation_service.dto.ReservationDTO;
import com.luxury.reservation_service.dto.ReservationRequest;
import com.luxury.reservation_service.model.Booking;
import com.luxury.reservation_service.model.Customer;
import com.luxury.reservation_service.model.Reservation;
import com.luxury.reservation_service.model.RoomType;
import com.luxury.reservation_service.repository.CustomerRepository;
import com.luxury.reservation_service.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.cloud.client.discovery.ReactiveDiscoveryClient.LOG;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;

    // Constructor for injecting the ReservationRepository dependency
    @Autowired
    public ReservationService(ReservationRepository reservationRepository ,
                              CustomerRepository customerRepository) {
        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
    }

    // Method to add a new reservation
    public Reservation addReservation(ReservationRequest reservationRequest) {
        try {
            // Create a new Reservation object using the data from the reservationRequest
            var newReservation = Reservation.builder()
                    .checkoutDate(reservationRequest.checkoutDate())
                    .checkinDate(reservationRequest.checkinDate())
                    .roomQuantity(reservationRequest.roomQuantity())
                    .customer(
                            Customer.builder()
                                    .customerId(reservationRequest.customerId()).build()
                    )
                    .roomType(
                            RoomType.builder()
                                    .roomTypeName(reservationRequest.roomTypeName()).build()
                    )
                    .build();

            // Save the new reservation and return it
            return reservationRepository.save(newReservation);
        } catch (Exception e) {
            // Handle any exceptions that occur during the reservation process
            throw new RuntimeException("Failed to add reservation", e);
        }
    }


    // Method to get a list of all reservations
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        // Fetch all bookings from the repository
        List<Reservation> reservations = reservationRepository.findAll();

        // Map the list of Booking entities to BookingDTOs
        List<ReservationDTO> reservationDTOs = reservations.stream().map(reservation -> {

            // Use the builder to create BookingDTO
            return ReservationDTO.builder()
                    .reservationID(reservation.getReservationId())
                    .roomTypeName(reservation.getRoomType().getRoomTypeName())// Set to null as we're not using RoomType directly in BookingDTO
                    .checkinDate(reservation.getCheckinDate())
                    .checkoutDate(reservation.getCheckoutDate())
                    .roomQuantity(reservation.getRoomQuantity())
                    .status("OnGoing") // Force status to "Booked"
                    .totalPrice(calculateTotalPrice(reservation)) // Use the room type price for calculation
                    .customer(reservation.getCustomer())
                    .build();
        }).collect(Collectors.toList());

        // Return the list of BookingDTOs with HTTP status 200 OK
        return new ResponseEntity<>(reservationDTOs, HttpStatus.OK);
    }

    private Double calculateTotalPrice(Reservation reservation) {

        // Calculate the number of days between check-in and check-out dates
        long dayCount = ChronoUnit.DAYS.between(reservation.getCheckinDate(), reservation.getCheckoutDate());
        double pricePerDay = reservation.getRoomType().getPricePerDay();
        Integer roomCount = reservation.getRoomQuantity();

        return dayCount*pricePerDay*roomCount;
    }

    public ResponseEntity<Void> removeReservation(Long reservationID) {
        try {

            Reservation reservation  = reservationRepository.findById(reservationID).orElse(null);
            // Check if the reservation exists
            if (reservation == null) {
                // Return 404 Not Found if the reservation does not exist
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            customerRepository.deleteById(reservation.getCustomer().getCustomerId());
            // Delete the reservation by ID
            reservationRepository.deleteById(reservationID);

            // Return 204 No Content after successful deletion
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOG.error("Error occurred while removing reservation", e);
            // Return 500 Internal Server Error in case of an exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
