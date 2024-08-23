package com.luxury.reservation_service.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomTypeDTO {

    private String roomTypeName;
    private Double pricePerDay;
}
