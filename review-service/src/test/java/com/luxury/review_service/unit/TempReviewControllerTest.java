package com.luxury.review_service.unit;

import com.luxury.review_service.controller.TempReviewController;
import com.luxury.review_service.model.TempReview;
import com.luxury.review_service.service.TempReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TempReviewControllerTest {

    @Mock
    private TempReviewService tempReviewService;

    @InjectMocks
    private TempReviewController tempReviewController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tempReviewController).build();
    }

    @Test
    void testAddTempReview() throws Exception {
        TempReview tempReview = new TempReview(1L, "John Doe", "Great product!");
        when(tempReviewService.addTempReview(any(TempReview.class))).thenReturn(tempReview);

        mockMvc.perform(post("/review/temp/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"review\":\"Great product!\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"John Doe\",\"review\":\"Great product!\"}"));

        verify(tempReviewService, times(1)).addTempReview(any(TempReview.class));
    }

    @Test
    void testGetAllTempReviews() throws Exception {
        List<TempReview> tempReviews = Arrays.asList(
                new TempReview(1L, "John Doe", "Great product!"),
                new TempReview(2L, "Jane Doe", "Not bad")
        );
        when(tempReviewService.getAllTempReviews()).thenReturn(tempReviews);

        mockMvc.perform(get("/review/temp/all"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"John Doe\",\"review\":\"Great product!\"},{\"id\":2,\"name\":\"Jane Doe\",\"review\":\"Not bad\"}]"));

        verify(tempReviewService, times(1)).getAllTempReviews();
    }

    @Test
    void testGetHello() throws Exception {
        mockMvc.perform(get("/review/temp/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello There!"));
    }
}
