package com.luxury.reservation_service.integration;

import com.luxury.reservation_service.dto.ReservationDTO;
import com.luxury.reservation_service.dto.ReservationRequest;
import com.luxury.reservation_service.model.Customer;
import com.luxury.reservation_service.model.Reservation;
import com.luxury.reservation_service.model.RoomType;
import com.luxury.reservation_service.repository.CustomerRepository;
import com.luxury.reservation_service.repository.ReservationRepository;
import com.luxury.reservation_service.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
class ReservationServiceIntegrationTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        // Clean up the database before each test to ensure tests are isolated
        reservationRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    @Transactional
    @Rollback
    void testAddReservation() {
        // Add a customer to the repository to satisfy the foreign key constraint
        Customer customer = Customer.builder().customerId(1L).build();
        customerRepository.save(customer);

        // Create the ReservationRequest
        ReservationRequest reservationRequest = new ReservationRequest(
                LocalDate.now(), LocalDate.now().plusDays(3), 1, 1L, "Single Room"
        );

        // Add the reservation
        Reservation savedReservation = reservationService.addReservation(reservationRequest);

        // Fetch from the database and verify
        Optional<Reservation> fetchedReservation = reservationRepository.findById(savedReservation.getReservationId());
        assertTrue(fetchedReservation.isPresent());
        assertEquals("Single Room", fetchedReservation.get().getRoomType().getRoomTypeName());
    }

    @Test
    @Transactional
    @Rollback
    void testGetAllReservations() {
        // Add a customer to the repository
        Customer customer = Customer.builder().customerId(1L).build();
        customerRepository.save(customer);

        // Create and save a Reservation
        RoomType roomType = RoomType.builder().roomTypeName("Single Room").pricePerDay(15000.00).build();
        Reservation reservation = Reservation.builder()
                .checkinDate(LocalDate.now())
                .checkoutDate(LocalDate.now().plusDays(3))
                .roomQuantity(1)
                .customer(customer)
                .roomType(roomType)
                .build();
        reservationRepository.save(reservation);

        // Fetch all reservations through the service
        ResponseEntity<List<ReservationDTO>> response = reservationService.getAllReservations();

        // Verify the response
        assertEquals(1, response.getBody().size());
        assertEquals("Single Room", response.getBody().get(0).getRoomTypeName());
    }

    @Test
    @Transactional
    @Rollback
    void testRemoveReservation() {
        // Add a customer and a reservation
        Customer customer = Customer.builder().customerId(1L).build();
        customerRepository.save(customer);

        RoomType roomType = RoomType.builder().roomTypeName("Single Room").pricePerDay(15000.00).build();
        Reservation reservation = Reservation.builder()
                .checkinDate(LocalDate.now())
                .checkoutDate(LocalDate.now().plusDays(3))
                .roomQuantity(1)
                .customer(customer)
                .roomType(roomType)
                .build();
        reservationRepository.save(reservation);

        // Remove the reservation
        ResponseEntity<Void> response = reservationService.removeReservation(reservation.getReservationId());

        // Verify the reservation and customer are deleted
        assertFalse(reservationRepository.findById(reservation.getReservationId()).isPresent());
        assertFalse(customerRepository.findById(customer.getCustomerId()).isPresent());
        assertEquals(204, response.getStatusCodeValue());  // 204 No Content
    }

    @Test
    @Transactional
    @Rollback
    void testRemoveReservationNotFound() {
        // Try to remove a reservation that does not exist
        ResponseEntity<Void> response = reservationService.removeReservation(100L);

        // Verify that a 404 Not Found status is returned
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @Transactional
    @Rollback
    void testCalculateTotalPrice() {
        // Create and save a reservation
        RoomType roomType = RoomType.builder().pricePerDay(15000.00).build();
        Reservation reservation = Reservation.builder()
                .checkinDate(LocalDate.now())
                .checkoutDate(LocalDate.now().plusDays(3))
                .roomQuantity(2)
                .roomType(roomType)
                .build();
        reservationRepository.save(reservation);

        // Calculate total price
        Double totalPrice = reservationService.calculateTotalPrice(reservation);

        // Verify the total price calculation
        assertEquals(90000.00, totalPrice);  // 3 days * 15000 per day * 2 rooms = 90000
    }
}
