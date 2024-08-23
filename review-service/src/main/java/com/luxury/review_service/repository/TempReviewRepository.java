package com.luxury.review_service.repository;

import com.luxury.review_service.model.TempReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempReviewRepository extends JpaRepository<TempReview, Long> {
}
