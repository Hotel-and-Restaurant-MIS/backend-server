package com.luxury.review_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
@ActiveProfiles(value = "test")
class ReviewServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}



