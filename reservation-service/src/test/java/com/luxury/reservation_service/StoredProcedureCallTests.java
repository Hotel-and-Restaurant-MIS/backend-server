package com.luxury.reservation_service;

import com.luxury.reservation_service.model.RoomCount;
import com.luxury.reservation_service.repository.RoomCountByCategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StoredProcedureCallTests {

    public static final Logger LOG = LoggerFactory.getLogger(StoredProcedureCallTests.class);

    @Autowired
    private RoomCountByCategoryRepository repository;

     @Test
     void validateResults() {
         Map<String, Object> data = repository.getAvailableRoomCount(LocalDate.of(2024, 8, 19), LocalDate.of(2024, 8, 19));
         List<Map<String, Object>> results = (List<Map<String, Object>>) data.get("#result-set-1");
         List<RoomCount> roomCounts = results.stream().map(result -> new RoomCount((String) result.get("room_type_name"), (Integer) result.get("available_count"))).toList();
         LOG.info(roomCounts.toString());
     }
}



