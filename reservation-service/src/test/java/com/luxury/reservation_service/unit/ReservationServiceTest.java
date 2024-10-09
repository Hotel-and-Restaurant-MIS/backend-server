package com.luxury.reservation_service.unit;

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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddReservation() {
        // Create a mock ReservationRequest object
        ReservationRequest reservationRequest = new ReservationRequest(
                LocalDate.now(), LocalDate.now().plusDays(3), 1, 1L, "Single Room"
        );

        // Create a mock Reservation entity to return when saved
        Reservation mockReservation = Reservation.builder()
                .reservationId(1L)
                .checkinDate(LocalDate.now())
                .checkoutDate(LocalDate.now().plusDays(3))
                .roomQuantity(1)
                .customer(Customer.builder().customerId(1L).build())
                .roomType(RoomType.builder().roomTypeName("Single Room").pricePerDay(15000.0).build())
                .build();

        when(reservationRepository.save(any(Reservation.class))).thenReturn(mockReservation);

        // Call the addReservation method
        Reservation result = reservationService.addReservation(reservationRequest);

        // Verify the result
        assertNotNull(result);
        assertEquals(1L, result.getReservationId());
        assertEquals("Single Room", result.getRoomType().getRoomTypeName());
    }

    @Test
    void testGetAllReservations() {
        // Create a mock list of reservations
        Reservation mockReservation = Reservation.builder()
                .reservationId(1L)
                .checkinDate(LocalDate.now())
                .checkoutDate(LocalDate.now().plusDays(3))
                .roomQuantity(1)
                .customer(Customer.builder().customerId(1L).build())
                .roomType(RoomType.builder().roomTypeName("Single Room").pricePerDay(100.0).build())
                .build();

        when(reservationRepository.findAll()).thenReturn(List.of(mockReservation));

        // Call the getAllReservations method
        ResponseEntity<List<ReservationDTO>> response = reservationService.getAllReservations();

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Single Room", response.getBody().get(0).getRoomTypeName());
    }

    @Test
    void testRemoveReservation() {
        // Create a mock reservation entity
        Reservation mockReservation = Reservation.builder()
                .reservationId(1L)
                .customer(Customer.builder().customerId(1L).build())
                .build();

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(mockReservation));

        // Call the removeReservation method
        ResponseEntity<Void> response = reservationService.removeReservation(1L);

        // Verify the repository interactions
        verify(reservationRepository, times(1)).deleteById(1L);
        verify(customerRepository, times(1)).deleteById(1L);

        // Verify the response
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testRemoveReservationNotFound() {
        // Return an empty optional to simulate reservation not found
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the removeReservation method
        ResponseEntity<Void> response = reservationService.removeReservation(1L);

        // Verify the response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Verify that deleteById is not called
        verify(reservationRepository, never()).deleteById(anyLong());
    }

    @Test
    void testCalculateTotalPrice() {
        // Create a mock reservation
        Reservation mockReservation = Reservation.builder()
                .checkinDate(LocalDate.now())
                .checkoutDate(LocalDate.now().plusDays(3))
                .roomQuantity(2)
                .roomType(RoomType.builder().pricePerDay(15000.0).build())
                .build();

        // Call the calculateTotalPrice method
        Double totalPrice = reservationService.calculateTotalPrice(mockReservation);

        // Verify the calculated total price
        assertEquals(90000, totalPrice);  // 3 days * 15000 per day * 2 rooms = 90000
    }
}
