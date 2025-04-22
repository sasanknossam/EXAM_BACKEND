package com.esa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.esa.entity.SeatingArrangement;

/**
 * Repository interface for Seating Arrangement entity.
 */
@Repository
public interface SeatingArrangementRepository extends JpaRepository<SeatingArrangement, Long> {
    // Find all seating arrangements for a given exam
    List<SeatingArrangement> findByExamId(Long examId);

    // Find seating arrangements for a specific student
    List<SeatingArrangement> findByStudentId(Long studentId);

    // Find seating arrangements for a specific room
    List<SeatingArrangement> findByRoomId(Long roomId);

	List<SeatingArrangement> findByRoomIdAndExamId(Long roomId, Long examId);
	Page<SeatingArrangement> findByRoomIdAndExamId(Long roomId, Long examId, Pageable pageable);
//	List<Student> findBySemesterAndBranchAndExamDate(String semester, String branch, LocalDate date);

	@Query("SELECT sa FROM SeatingArrangement sa WHERE sa.exam.id = :examId AND sa.student.id = :studentId")
	List<SeatingArrangement> findByExamIdAndStudentId(@Param("examId") Long examId, @Param("studentId") Long studentId);

	
	

}

