package com.luxury.review_service.controller;

import com.luxury.review_service.dto.ReviewDTO;
import com.luxury.review_service.model.Review;
import com.luxury.review_service.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/all")
    public List<ReviewDTO> getAllTempReviews() {
        return reviewService.getAllReviews();
    }
}
