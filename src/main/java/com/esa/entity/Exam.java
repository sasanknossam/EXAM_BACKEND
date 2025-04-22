package com.esa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Represents an Exam entity with details about the examination.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exams")
@Builder
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate examDate; // Date of the exam
    private int semester; // Semester for which the exam is conducted
    private String branch; // Branch associated with the exam

    @Column(nullable = false)
    private String subjectCode; // Unique code for the subject of the exam

    private int year; // Year of study (1st, 2nd, or 3rd year)
}
