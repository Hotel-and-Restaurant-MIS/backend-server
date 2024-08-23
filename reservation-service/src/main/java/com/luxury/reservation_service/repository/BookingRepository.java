package com.luxury.reservation_service.repository;

import com.luxury.reservation_service.model.Booking;
import com.luxury.reservation_service.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByRoomTypeName(RoomType roomTypeName);
}
