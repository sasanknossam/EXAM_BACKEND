package com.esa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A data model for transferring seating arrangement information.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatingArrangementDTO {
	private Long id;
    private Long examId; // Identifier for the exam
    private Long roomId; // Identifier for the room
    private Long studentId; // Identifier for the student
    private Integer seatNumber;
    private String pin; // Optional (you can remove this if StudentDTO contains it)
    private StudentDTO student;
}
