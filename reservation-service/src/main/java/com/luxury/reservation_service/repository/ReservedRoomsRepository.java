package com.luxury.reservation_service.repository;

import com.luxury.reservation_service.model.Booking;
import com.luxury.reservation_service.model.ReservedRooms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservedRoomsRepository extends JpaRepository<ReservedRooms, Long> {
    void deleteByBooking(Booking booking);
}
