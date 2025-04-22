package com.esa.model;

import lombok.Data;

import java.time.LocalDate;

/**
 * A data model for transferring exam information.
 */
@Data
public class ExamDTO {
	private Long id;
    private LocalDate examDate; // Date of the exam
    private int semester; // Semester for which the exam is conducted
    private String branch; // Branch associated with the exam
    private String subjectCode; // Unique code for the subject of the exam
    private int year; // Year of study (1st, 2nd, or 3rd year)
}
