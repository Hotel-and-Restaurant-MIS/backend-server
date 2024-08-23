package com.luxury.reservation_service.dto;

import java.time.LocalDate;

public record ReservationRequest(
        LocalDate checkinDate,
        LocalDate checkoutDate,
        Integer roomQuantity,
        Long customerId,
        String roomTypeName
) {
}
