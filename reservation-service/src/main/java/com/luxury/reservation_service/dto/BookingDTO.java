package com.luxury.reservation_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.luxury.reservation_service.model.Customer;
import com.luxury.reservation_service.model.RoomType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BookingDTO {
    private Long bookingID;
    private String roomTypeName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkinDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkoutDate;
    private Integer roomQuantity;
    private String status;
    private Double totalPrice;
    private Customer customer;

    public BookingDTO() {
        this.status = "Completed";  // Always set status to "Booked"
    }
}
