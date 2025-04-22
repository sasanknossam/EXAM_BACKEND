package com.esa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A data model for transferring room information.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO{
	private Long id;
    private String roomNumber; // Identifier for the room
    private int capacity; // Maximum seating capacity of the room
}

