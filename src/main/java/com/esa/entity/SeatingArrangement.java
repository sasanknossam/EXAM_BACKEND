package com.esa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a Seating Arrangement entity linking students to exams and rooms.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seating_arrangements")
public class SeatingArrangement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Exam exam; // Exam details

    @ManyToOne
    private Room room; // Room details

    @ManyToOne
    private Student student; // Student details
    
    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber;

   
}
