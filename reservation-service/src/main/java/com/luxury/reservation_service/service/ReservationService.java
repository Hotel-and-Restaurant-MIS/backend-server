package com.luxury.reservation_service.service;

import com.luxury.reservation_service.dto.ReservationRequest;
import com.luxury.reservation_service.model.Customer;
import com.luxury.reservation_service.model.Reservation;
import com.luxury.reservation_service.model.RoomType;
import com.luxury.reservation_service.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    // Constructor for injecting the ReservationRepository dependency
    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
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
    public List<Reservation> getAllReservations() {
        // Retrieve and return all reservations
        return reservationRepository.findAll();
    }
}
