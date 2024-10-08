package com.luxury.review_service.unit;

import com.luxury.review_service.model.TempReview;
import com.luxury.review_service.repository.TempReviewRepository;
import com.luxury.review_service.service.TempReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TempReviewServiceTest {

    @Mock
    private TempReviewRepository tempReviewRepository;

    @InjectMocks
    private TempReviewService tempReviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTempReview() {
        TempReview tempReview = new TempReview(1L, "John Doe", "Great product!");
        when(tempReviewRepository.save(tempReview)).thenReturn(tempReview);

        TempReview result = tempReviewService.addTempReview(tempReview);

        assertEquals(tempReview, result);
        verify(tempReviewRepository, times(1)).save(tempReview);
    }

    @Test
    void testGetAllTempReviews() {
        List<TempReview> tempReviews = Arrays.asList(
                new TempReview(1L, "John Doe", "Great product!"),
                new TempReview(2L, "Jane Doe", "Not bad")
        );
        when(tempReviewRepository.findAll()).thenReturn(tempReviews);

        List<TempReview> result = tempReviewService.getAllTempReviews();

        assertEquals(tempReviews, result);
        verify(tempReviewRepository, times(1)).findAll();
    }
}
