package com.esa.controller;

import com.esa.model.StudentDTO;
import com.esa.service.StudentService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing student-related operations.
 */
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@CrossOrigin( "https://esa-si4g.onrender.com")
public class StudentController {

    private final StudentService studentService;

    /**
     * Endpoint to create a new student.
     * 
     * @param studentDTO the student data transfer object
     * @return ResponseEntity with the created student data
     */
    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
    	 if (studentService.isPinExists(studentDTO.getPin())) {
    		 System.out.println("Pin Already Exists");
    	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    	    }
        StudentDTO createdStudent = studentService.createStudent(studentDTO);
        return ResponseEntity.status(201).body(createdStudent);
    }

    /**
     * Endpoint to get a student by their PIN.
     * 
     * @param pin the student's PIN
     * @return ResponseEntity with the student data
     */
    @GetMapping("/{pin}")
    public ResponseEntity<StudentDTO> getStudentByPin(@PathVariable String pin) {
        StudentDTO student = studentService.getStudentByPin(pin);
        return ResponseEntity.ok(student);
    }

    /**
     * Endpoint to get all students.
     * 
     * @return ResponseEntity with the list of all students
     */
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    /**
     * Endpoint to update a student's information.
     * 
     * @param pin the student's PIN
     * @param studentDTO the student data transfer object
     * @return ResponseEntity with the updated student data
     */
    @PutMapping("/{pin}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable String pin, @RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudent = studentService.updateStudent(pin, studentDTO);
        return ResponseEntity.ok(updatedStudent);
    }

    /**
     * Endpoint to delete a student by their PIN.
     * 
     * @param pin the student's PIN
     * @return ResponseEntity indicating the deletion status
     */
    @DeleteMapping("/{pin}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String pin) {
        studentService.deleteStudent(pin);
        return ResponseEntity.noContent().build();
    }
    
    
    
}
