package com.esa.controller;

import com.esa.entity.Room;
import com.esa.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing room-related operations.
 */
@RestController
@RequestMapping("https://mild-sile-sasanknossam-1168063f.koyeb.app/api/rooms")
@CrossOrigin(origins="https://esa-si4g.onrender.com")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    /**
     * Endpoint to create a new room.
     * 
     * @param room the room entity
     * @return ResponseEntity with the created room data
     */
    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room createdRoom = roomService.createRoom(room);
        return ResponseEntity.status(201).body(createdRoom);
    }

    /**
     * Endpoint to get a room by its number.
     * 
     * @param roomNumber the room number
     * @return ResponseEntity with the room data
     */
    @GetMapping("/{roomNumber}")
    public ResponseEntity<Room> getRoomByNumber(@PathVariable String roomNumber) {
        Room room = roomService.getRoomByNumber(roomNumber);
        return ResponseEntity.ok(room);
    }

    /**
     * Endpoint to get all rooms.
     * 
     * @return ResponseEntity with the list of all rooms
     */
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    /**
     * Endpoint to update a room's information.
     * 
     * @param roomNumber the room number
     * @param updatedRoom the updated room entity
     * @return ResponseEntity with the updated room data
     */
    @PutMapping("/{roomNumber}")
    public ResponseEntity<Room> updateRoom(@PathVariable String roomNumber, @RequestBody Room updatedRoom) {
        Room updatedRoomData = roomService.updateRoom(roomNumber, updatedRoom);
        return ResponseEntity.ok(updatedRoomData);
    }

    /**
     * Endpoint to delete a room by its number.
     * 
     * @param roomNumber the room number
     * @return ResponseEntity indicating the deletion status
     */
    @DeleteMapping("/{roomNumber}")
    public ResponseEntity<Void> deleteRoom(@PathVariable String roomNumber) {
        roomService.deleteRoom(roomNumber);
        return ResponseEntity.noContent().build();
    }
}
