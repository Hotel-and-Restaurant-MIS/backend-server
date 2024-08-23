package com.luxury.review_service.controller;

import com.luxury.review_service.model.TempReview;
import com.luxury.review_service.service.TempReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/review/temp")
public class TempReviewController {

    private final TempReviewService tempReviewService;

    @Autowired
    public TempReviewController(TempReviewService tempReviewService){this.tempReviewService = tempReviewService;}

    @PostMapping("/add")
        public TempReview addTempReview(@RequestBody TempReview tempReview) {
        return tempReviewService.addTempReview(tempReview);
    }

    @GetMapping("/all")
    public List<TempReview> getAllTempReviews() {
        return tempReviewService.getAllTempReviews();
    }
}
