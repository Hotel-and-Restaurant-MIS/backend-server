package com.luxury.review_service.controller;

import com.luxury.review_service.dto.ReviewDTO;
import com.luxury.review_service.model.TempReview;
import com.luxury.review_service.service.TempReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/review/temp")
public class TempReviewController {

    private final TempReviewService tempReviewService;

    @Autowired
    public TempReviewController(TempReviewService tempReviewService) {
        this.tempReviewService = tempReviewService;
    }

    @PostMapping("/add")
    public TempReview addTempReview(@RequestBody TempReview tempReview) {
        return tempReviewService.addTempReview(tempReview);
    }

    @GetMapping("/all")
    public List<ReviewDTO> getAllTempReviews() {
        return tempReviewService.getAllTempReviews();
    }

    @GetMapping("/hello")
    public String getHello() {
        return "Hello There!";
    }

    @PostMapping("/approve")
    public ResponseEntity<Void> approveTempReview(@RequestParam Long tempReviewId) {
        return tempReviewService.approveTempReview(tempReviewId);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteTempReview(@RequestParam Long tempReviewId) {
        return tempReviewService.rejectTempReview(tempReviewId);
    }

}
