package com.luxury.review_service.service;

import com.luxury.review_service.dto.ReviewDTO;
import com.luxury.review_service.model.Review;
import com.luxury.review_service.model.TempReview;
import com.luxury.review_service.repository.ReviewRepository;
import com.luxury.review_service.repository.TempReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final TempReviewRepository tempReviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository,
                         TempReviewRepository tempReviewRepository) {
        this.reviewRepository = reviewRepository;
        this.tempReviewRepository = tempReviewRepository;
    }

    public List<ReviewDTO> getAllReviews() {

        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        List<Review> reviews = reviewRepository.findAll();
        for (Review review : reviews) {
            // Assuming you have a builder pattern in ReviewDTO
            ReviewDTO reviewDTO = ReviewDTO.builder()
                    .id(review.getId())
                    .name(review.getName())
                    .review(review.getReview())
                    .status("Approved")
                    .build();

            reviewDTOList.add(reviewDTO);
        }
        return reviewDTOList;
    }
}
