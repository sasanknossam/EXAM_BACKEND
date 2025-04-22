package com.esa.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a Student entity with details for seating arrangement.
 */

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String pin; // Unique identifier for the student

    private String name;
    private String branch;
    private int semester;
    private String collegeCode;
    private int year; // Year of study (1st, 2nd, or 3rd year)
    @Column(name = "dob")
    @Temporal(TemporalType.DATE) // Store DOB as Date type in the database
    private Date dob;
}
