package com.esa.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A data model for transferring student information.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
	private Long id;
    private String pin; // Unique identifier for the student
    private String name;
    private String branch;
    private int semester;
    private String collegeCode;//022
    private int year; // Year of study (1st, 2nd, or 3rd year)
    private Date dob;
}

