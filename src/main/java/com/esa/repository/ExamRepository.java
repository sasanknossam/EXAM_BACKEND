package com.esa.repository;

import com.esa.entity.Exam;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Exam entity.
 */
@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    // Example custom query: Find exams by semester and branch
    Optional<Exam> findBySemesterAndBranch(int semester, String branch);
  
//    List<Exam> findByExamDate(String examDate);

//    List<Exam> findByExamByDate(LocalDate examDate);


}				
