package com.esa.service;

import com.esa.entity.Room;

import java.util.List;

/**
 * Interface for Room service operations.
 */
public interface RoomService {
    Room createRoom(Room room);

    Room getRoomByNumber(String roomNumber);

    List<Room> getAllRooms();

    Room updateRoom(String roomNumber, Room updatedRoom);

    void deleteRoom(String roomNumber);
}
