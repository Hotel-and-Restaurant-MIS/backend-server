package com.luxury.virtualwaiter_service.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusDTO {
    private Long orderId;
    private String status;
}
