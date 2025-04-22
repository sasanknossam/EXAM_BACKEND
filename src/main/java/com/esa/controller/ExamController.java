package com.esa.controller;

import com.esa.model.ExamDTO;
import com.esa.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing exam-related operations.
 */
@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
@CrossOrigin("https://exambackend-production.up.railway.app")
public class ExamController {

    private final ExamService examService;

    /**
     * Endpoint to create a new exam.
     * 
     * @param examDTO the exam data transfer object
     * @return ResponseEntity with the created exam data
     */
    @PostMapping
    public ResponseEntity<ExamDTO> createExam(@RequestBody ExamDTO examDTO) {
        ExamDTO createdExam = examService.createExam(examDTO);
        return ResponseEntity.status(201).body(createdExam);
    }

    /**
     * Endpoint to get an exam by its ID.
     * 
     * @param id the exam ID
     * @return ResponseEntity with the exam data
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExamDTO> getExamById(@PathVariable Long id) {
        ExamDTO exam = examService.getExamById(id);
        return ResponseEntity.ok(exam);
    }

    /**
     * Endpoint to get all exams.
     * 
     * @return ResponseEntity with the list of all exams
     */
    @GetMapping
    public ResponseEntity<List<ExamDTO>> getAllExams() {
        List<ExamDTO> exams = examService.getAllExams();
        return ResponseEntity.ok(exams);
    }

    /**
     * Endpoint to update an exam's information.
     * 
     * @param id the exam ID
     * @param examDTO the exam data transfer object
     * @return ResponseEntity with the updated exam data
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExamDTO> updateExam(@PathVariable Long id, @RequestBody ExamDTO examDTO) {
        ExamDTO updatedExam = examService.updateExam(id, examDTO);
        return ResponseEntity.ok(updatedExam);
    }

    /**
     * Endpoint to delete an exam by its ID.
     * 
     * @param id the exam ID
     * @return ResponseEntity indicating the deletion status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
        return ResponseEntity.noContent().build();
    }
}
