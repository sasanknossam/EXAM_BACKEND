package com.esa.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.esa.model.SeatingArrangementDTO;
import com.esa.model.StudentDTO;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Interface for Seating Arrangement service operations.
 */
public interface SeatingArrangementService {
    SeatingArrangementDTO assignSeat(Long examId, Long roomId, Long studentId);

    List<SeatingArrangementDTO> getSeatingByExam(Long examId);

    List<SeatingArrangementDTO> getSeatingByRoom(Long roomId);

    List<SeatingArrangementDTO> getSeatingByStudent(Long studentId);
    List<StudentDTO> getStudentsByRoomAndExam(Long roomId, Long examId);

	Map<String, Object> getRoomExamSummary(Long roomId, Long examId);
	Page<StudentDTO> getStudentsByRoomAndExamPaginated(Long roomId, Long examId, int page, int size);
	List<StudentDTO> searchStudents(Long examId, String name, String pin);
	
	void exportSeatingArrangementsToExcel(Long roomId, Long examId, HttpServletResponse response) throws IOException;

	void exportToExcel(HttpServletResponse response, Long roomId, Long examId) throws IOException;
//	List<SeatingArrangementDTO> autoAssignSeats(Long examId, String roomId);
//	List<Student> students = studentRepository.findBySemesterAndBranchAndExamDate(semester, branch, date);

//List<SeatingArrangementDTO> autoAssignSeats(Long examId) throws Exception;
//	 public List<SeatingArrangementDTO> autoAssignSeats(Long examId) {
//	        // Implementation for auto-assigning seats
//	        Exam exam = examRepository.findById(examId)
//	                .orElseThrow(() -> new RuntimeException("Exam not found"));
//	        List<Room> rooms = roomRepository.findAll();
//	        List<Student> students = studentRepository.findByExamId(examId);
//
//	
//}

    // New method to handle auto-assignment for one or more exams
    List<SeatingArrangementDTO> autoAssignSeats(List<Long> examIds, Long roomId) throws Exception;

    // Existing autoAssign method (you might want to rename or overload)
//    List<SeatingArrangementDTO> autoAssignSeats(Long examId, Long roomId) throws Exception;
}