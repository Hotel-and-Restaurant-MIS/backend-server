package com.luxury.review_service.unit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
@ActiveProfiles(value = "test")
public class ReviewServiceUnitTests {

    @Test
    void test(){
        assert 1 == 1;
    }
}
