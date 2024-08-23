package com.luxury.reservation_service.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomCount {
    private String roomTypeName;
    private Integer availableCount;
}
