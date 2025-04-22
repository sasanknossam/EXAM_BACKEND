package com.esa.service;

import com.esa.model.StudentDTO;
import java.util.List;

/**
 * Service interface for managing Students.
 */
public interface StudentService {
    StudentDTO createStudent(StudentDTO studentDTO);
    StudentDTO getStudentByPin(String pin);
    List<StudentDTO> getAllStudents();
    StudentDTO updateStudent(String pin, StudentDTO studentDTO);
    void deleteStudent(String pin);
    boolean isPinExists(String pin);
}
