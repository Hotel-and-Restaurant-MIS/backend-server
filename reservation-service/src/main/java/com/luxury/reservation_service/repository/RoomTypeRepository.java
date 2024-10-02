package com.luxury.reservation_service.repository;

import com.luxury.reservation_service.dto.RoomTypeDTO;
import com.luxury.reservation_service.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, String> {

    @Query("SELECT new com.luxury.reservation_service.dto.RoomTypeDTO(r.roomTypeName, r.pricePerDay) " +
            "FROM RoomType r")
    List<RoomTypeDTO> findAllRoomTypesDTO();

    Optional<RoomType> findRoomTypeByRoomTypeName(String roomTypeName);
}
