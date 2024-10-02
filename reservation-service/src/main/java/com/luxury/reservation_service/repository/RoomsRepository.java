package com.luxury.reservation_service.repository;

import com.luxury.reservation_service.model.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomsRepository extends JpaRepository<Rooms, Long> {
}
