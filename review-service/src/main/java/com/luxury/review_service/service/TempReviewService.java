package com.luxury.review_service.service;

import com.luxury.review_service.model.TempReview;
import com.luxury.review_service.repository.TempReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TempReviewService {

    private final TempReviewRepository tempReviewRepository;

    @Autowired
    public TempReviewService(TempReviewRepository tempReviewRepository) {
        this.tempReviewRepository = tempReviewRepository;
    }

    public TempReview addTempReview(TempReview tempReview) {
        return tempReviewRepository.save(tempReview);
    }

    public List<TempReview> getAllTempReviews() {
        return tempReviewRepository.findAll();
    }
}
