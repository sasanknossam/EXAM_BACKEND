package com.esa.service.impl;

import com.esa.entity.Room;
import com.esa.exception.ResourceNotFoundException;
import com.esa.repository.RoomRepository;
import com.esa.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of RoomService.
 */
@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room getRoomByNumber(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with number: " + roomNumber));
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room updateRoom(String roomNumber, Room updatedRoom) {
        Room existingRoom = getRoomByNumber(roomNumber);
        existingRoom.setCapacity(updatedRoom.getCapacity());
        return roomRepository.save(existingRoom);
    }

    @Override
    public void deleteRoom(String roomNumber) {
        Room room = getRoomByNumber(roomNumber);
        roomRepository.delete(room);
    }
}
