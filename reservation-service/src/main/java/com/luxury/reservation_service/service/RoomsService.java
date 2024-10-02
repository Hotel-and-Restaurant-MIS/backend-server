package com.luxury.reservation_service.service;

import com.luxury.reservation_service.model.Rooms;
import com.luxury.reservation_service.repository.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomsService {

    private final RoomsRepository roomsRepository;

    @Autowired
    public RoomsService(RoomsRepository roomsRepository) {
        this.roomsRepository = roomsRepository;
    }

    public List<String> getAvailableRooms(String roomTypeName, LocalDate checkInDate, LocalDate checkOutDate, int noOfRooms) {
        List<Rooms> availableRooms = roomsRepository.findAvailableRooms(roomTypeName, checkInDate, checkOutDate);

        // Sort rooms by their IDs (assuming RoomId is of a comparable type)
        List<Rooms> sortedRooms = availableRooms.stream()
                .sorted((r1, r2) -> r1.getRoomId().compareTo(r2.getRoomId()))
                .collect(Collectors.toList());

        // Find consecutive room numbers
        List<String> roomNumbers = sortedRooms.stream()
                .map(room -> room.getRoomId().toString())
                .collect(Collectors.toList());

        // Check for consecutive rooms
        List<String> consecutiveRooms = new ArrayList<>();
        for (int i = 0; i < roomNumbers.size(); i++) {
            if (consecutiveRooms.size() < noOfRooms) {
                // Check if the current room can be added to consecutiveRooms
                if (i == 0 || Integer.parseInt(roomNumbers.get(i)) == Integer.parseInt(roomNumbers.get(i - 1)) + 1) {
                    consecutiveRooms.add(roomNumbers.get(i));
                } else {
                    // If not consecutive, reset the list
                    consecutiveRooms.clear();
                    consecutiveRooms.add(roomNumbers.get(i));
                }
            }
        }

        // Return the desired number of consecutive rooms
        return consecutiveRooms.stream().limit(noOfRooms).collect(Collectors.toList());
    }


}
