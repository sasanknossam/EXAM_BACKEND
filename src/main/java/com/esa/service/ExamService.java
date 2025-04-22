package com.esa.service;

import com.esa.model.ExamDTO;

import java.util.List;

/**
 * Service interface for Exam operations.
 */
public interface ExamService {
    /**
     * Create a new exam.
     *
     * @param examDTO the exam details
     * @return the created exam
     */
    ExamDTO createExam(ExamDTO examDTO);

    /**
     * Get details of an exam by ID.
     *
     * @param id the exam ID
     * @return the exam details
     */
    ExamDTO getExamById(Long id);

    /**
     * Get a list of all exams.
     *
     * @return list of exams
     */
    List<ExamDTO> getAllExams();

    /**
     * Update an existing exam by ID.
     *
     * @param id      the exam ID
     * @param examDTO the updated exam details
     * @return the updated exam
     */
    ExamDTO updateExam(Long id, ExamDTO examDTO);

    /**
     * Delete an exam by ID.
     *
     * @param id the exam ID
     */
    void deleteExam(Long id);
    
//    List<ExamDTO> getExamsByDate(String date);

}
