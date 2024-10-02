package com.luxury.reservation_service.repository;

import com.luxury.reservation_service.model.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoomsRepository extends JpaRepository<Rooms, Long> {

    @Query("SELECT r FROM Rooms r WHERE r.roomType.roomTypeName = :roomTypeName " +
            "AND r.RoomId NOT IN (SELECT rr.rooms.RoomId FROM ReservedRooms rr " +
            "WHERE rr.booking.checkinDate < :checkOutDate AND rr.booking.checkoutDate > :checkInDate)")
    List<Rooms> findAvailableRooms(@Param("roomTypeName") String roomTypeName,
                                   @Param("checkInDate") LocalDate checkInDate,
                                   @Param("checkOutDate") LocalDate checkOutDate);
}

