package com.luxury.reservation_service.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingRequestDTO {
    private String name;
    private String email;
    private String phoneNumber;
    private String nic;
    private String noOfRooms;
    private String checkInDate;
    private String checkOutDate;
    private String roomType;
    private List<String> rooms;
    private String totalPrice;
}
