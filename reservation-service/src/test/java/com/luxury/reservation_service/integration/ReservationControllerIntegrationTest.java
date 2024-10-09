package com.luxury.reservation_service.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxury.reservation_service.dto.ReservationRequest;
import com.luxury.reservation_service.model.Customer;
import com.luxury.reservation_service.model.Reservation;
import com.luxury.reservation_service.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestPropertySource(properties = "spring.config.import=optional:configserver:")
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ReservationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservationRepository.deleteAll();
    }

    @Test
    void shouldAddReservation() throws Exception {
        ReservationRequest request = new ReservationRequest(
                LocalDate.of(2024, 10, 10),
                LocalDate.of(2024, 10, 15),
                2,
                1L,
                "Luxury Suite"
        );

        mockMvc.perform(post("/reservations/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomQuantity").value(2))
                .andExpect(jsonPath("$.checkinDate").value("2024-10-10"))
                .andExpect(jsonPath("$.checkoutDate").value("2024-10-15"));
    }

    @Test
    void shouldGetAllReservations() throws Exception {
        Reservation reservation = Reservation.builder()
                .checkinDate(LocalDate.of(2024, 10, 10))
                .checkoutDate(LocalDate.of(2024, 10, 15))
                .roomQuantity(2)
                .customer(Customer.builder().customerId(1L).build())
                .build();
        reservationRepository.saveAll(List.of(reservation));

        mockMvc.perform(get("/reservations/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].roomQuantity").value(2));
    }

    @Test
    void shouldRemoveReservation() throws Exception {
        Reservation reservation = Reservation.builder()
                .checkinDate(LocalDate.of(2024, 10, 10))
                .checkoutDate(LocalDate.of(2024, 10, 15))
                .roomQuantity(2)
                .customer(Customer.builder().customerId(1L).build())
                .build();
        Reservation savedReservation = reservationRepository.save(reservation);

        mockMvc.perform(delete("/reservations/remove")
                        .param("reservationId", String.valueOf(savedReservation.getReservationId())))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/reservations/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

}
