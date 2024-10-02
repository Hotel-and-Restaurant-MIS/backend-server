package com.luxury.reservation_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomsRequestDTO {
    private String noOfRooms;
    private String checkInDate;
    private String checkOutDate;
    private String roomType;

}
