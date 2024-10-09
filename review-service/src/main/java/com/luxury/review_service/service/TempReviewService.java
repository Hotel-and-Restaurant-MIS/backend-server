package com.luxury.review_service.service;

import com.luxury.review_service.dto.ReviewDTO;
import com.luxury.review_service.model.Review;
import com.luxury.review_service.model.TempReview;
import com.luxury.review_service.repository.ReviewRepository;
import com.luxury.review_service.repository.TempReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TempReviewService {

    private final TempReviewRepository tempReviewRepository;
    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;

    @Autowired
    public TempReviewService(TempReviewRepository tempReviewRepository,
                             ReviewService reviewService, ReviewRepository reviewRepository) {
        this.tempReviewRepository = tempReviewRepository;
        this.reviewService = reviewService;
        this.reviewRepository = reviewRepository;
    }

    public TempReview addTempReview(TempReview tempReview) {
        return tempReviewRepository.save(tempReview);
    }

    public List<ReviewDTO> getAllTempReviews() {
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        List<TempReview> tempReviews = tempReviewRepository.findAll();
        for (TempReview tempReview : tempReviews) {
            // Assuming you have a builder pattern in ReviewDTO
            ReviewDTO reviewDTO = ReviewDTO.builder()
                    .id(tempReview.getId())
                    .name(tempReview.getName())
                    .review(tempReview.getReview())
                    .status("Pending")
                    .build();

            reviewDTOList.add(reviewDTO);
        }
        return reviewDTOList;
    }

    public ResponseEntity<Void> approveTempReview(Long tempReviewId) {

        TempReview tempReview = tempReviewRepository.findById(tempReviewId).orElse(null);
        if (tempReview == null) {
            return ResponseEntity.notFound().build();
        }

        Review newReview = Review.builder()
                .name(tempReview.getName())
                .review(tempReview.getReview())
        .build();

        reviewRepository.save(newReview);
        tempReviewRepository.deleteById(tempReviewId);

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Void> rejectTempReview(Long tempReviewId) {
        TempReview tempReview = tempReviewRepository.findById(tempReviewId).orElse(null);
        if (tempReview == null) {
            return ResponseEntity.notFound().build();
        }
        tempReviewRepository.deleteById(tempReviewId);
        return ResponseEntity.noContent().build();
    }


}
