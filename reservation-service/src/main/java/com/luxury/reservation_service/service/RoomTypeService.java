package com.luxury.reservation_service.service;

import com.luxury.reservation_service.dto.RoomTypeDTO;
import com.luxury.reservation_service.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

    @Autowired
    public RoomTypeService(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    public List<RoomTypeDTO> getAllRoomTypePrice() {
        return roomTypeRepository.findAllRoomTypesDTO();
    }

}
