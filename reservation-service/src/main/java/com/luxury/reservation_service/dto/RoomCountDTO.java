package com.luxury.reservation_service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomCountDTO {
    private String roomTypeName;
    private Long availableCount;

}
