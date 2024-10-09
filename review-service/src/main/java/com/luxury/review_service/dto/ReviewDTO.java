package com.luxury.review_service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {
    private Long id;
    private String name;
    private String review;
    private String status;
}
