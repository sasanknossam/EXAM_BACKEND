package com.esa.controller;

import com.esa.model.SeatingArrangementDTO;
import com.esa.model.StudentDTO;
import com.esa.service.SeatingArrangementService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Controller for managing Seating Arrangements.
 */
@RestController
@RequestMapping("/api/seating-arrangements")
@RequiredArgsConstructor
@CrossOrigin("https://exambackend-production.up.railway.app/0")
public class SeatingArrangementController {

    private final SeatingArrangementService seatingArrangementService;

    /**
     * Assign a seat to a student for an exam and room.
     *
     * @param examId the ID of the exam
     * @param roomId the ID of the room
     * @param studentId the ID of the student
     * @return the seating arrangement DTO
     */
    @PostMapping("/assign")
    @ResponseStatus(HttpStatus.CREATED)
    public SeatingArrangementDTO assignSeat(
            @RequestParam Long examId,
            @RequestParam Long roomId,
            @RequestParam Long studentId) {
        return seatingArrangementService.assignSeat(examId, roomId, studentId);
    }

    /**
     * Get seating arrangements by exam ID.
     *
     * @param examId the ID of the exam
     * @return the list of seating arrangement DTOs
     */
    @GetMapping("/exam/{examId}")
    public List<SeatingArrangementDTO> getSeatingByExam(@PathVariable Long examId) {
        return seatingArrangementService.getSeatingByExam(examId);
    }

    /**
     * Get seating arrangements by room ID.
     *
     * @param roomId the ID of the room
     * @return the list of seating arrangement DTOs
     */
    @GetMapping("/room/{roomId}")
    public List<SeatingArrangementDTO> getSeatingByRoom(@PathVariable Long roomId) {
        return seatingArrangementService.getSeatingByRoom(roomId);
    }

    /**
     * Get seating arrangements by student ID.
     *
     * @param studentId the ID of the student
     * @return the list of seating arrangement DTOs
     */
    @GetMapping("/student/{studentId}")
    public List<SeatingArrangementDTO> getSeatingByStudent(@PathVariable Long studentId) {
        return seatingArrangementService.getSeatingByStudent(studentId);
    }
    
    
    /**
     * Get a list of students based on room ID and exam ID.
     *
     * @param roomId the room ID
     * @param examId the exam ID
     * @return the list of students
     */
    @GetMapping("/room/{roomId}/exam/{examId}/students")
    public List<StudentDTO> getStudentsByRoomAndExam(@PathVariable Long roomId, @PathVariable Long examId) {
        return seatingArrangementService.getStudentsByRoomAndExam(roomId, examId);
    }
    
    @GetMapping("/summary/room/{roomId}/exam/{examId}")
    public ResponseEntity<Map<String, Object>> getRoomExamSummary(@PathVariable Long roomId, @PathVariable Long examId) {
        Map<String, Object> summary = seatingArrangementService.getRoomExamSummary(roomId, examId);
        return ResponseEntity.ok(summary);
    }
    
    @GetMapping("/students/room/{roomId}/exam/{examId}")
    public ResponseEntity<Page<StudentDTO>> getStudentsByRoomAndExamPaginated(
            @PathVariable Long roomId,
            @PathVariable Long examId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<StudentDTO> students = seatingArrangementService.getStudentsByRoomAndExamPaginated(roomId, examId, page, size);
        return ResponseEntity.ok(students);
    }
    
    @GetMapping("/students/search")
    public ResponseEntity<List<StudentDTO>> searchStudents(
        @RequestParam Long examId,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String pin) {
        List<StudentDTO> students = seatingArrangementService.searchStudents(examId, name, pin);
        return ResponseEntity.ok(students);
    }

    
    @GetMapping("/export")
    public void exportSeatingArrangementsToExcel(
            @RequestParam Long roomId,
            @RequestParam Long examId,
            HttpServletResponse response) throws IOException {
        seatingArrangementService.exportSeatingArrangementsToExcel(roomId, examId, response);
    }
    
    @GetMapping("/export-excel")
    public void exportToExcel(HttpServletResponse response, 
                              @RequestParam Long roomId, 
                              @RequestParam Long examId) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String headerValue = "attachment; filename=seating-arrangements.xlsx";
        response.setHeader("Content-Disposition", headerValue);

        seatingArrangementService.exportToExcel(response, roomId, examId);
    }
 // New endpoint to handle auto-assignment for a list of exam IDs
    @CrossOrigin("http://localhost:4200")
    @PostMapping("/auto-assign")
    public ResponseEntity<?> autoAssignSeatsForMultipleExams(
            @RequestParam List<Long> examIds,
            @RequestParam Long roomId) {
        try {
            List<SeatingArrangementDTO> result = seatingArrangementService.autoAssignSeats(examIds, roomId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

//     Existing auto-assign endpoint for a single exam (you might want to keep this for single exam scenarios)
    @PostMapping("/auto-assign/{examId}")
    public ResponseEntity<?> autoAssignSeatsForSingleExam(
            @PathVariable Long examId,
            @RequestParam Long roomId) {
        try {
            List<SeatingArrangementDTO> result = seatingArrangementService.autoAssignSeats(List.of(examId), roomId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
//   
//    @PostMapping("/auto-assign")
//    public ResponseEntity<List<SeatingArrangementDTO>> autoAssignMultiple(
//            @RequestBody Map<String, Object> payload) throws Exception {
//        List<Integer> examIdsInt = (List<Integer>) payload.get("examIds");
//        Long roomId = Long.valueOf(payload.get("roomId").toString());
//        List<Long> examIds = examIdsInt.stream().map(Long::valueOf).toList();
//        return ResponseEntity.ok(seatingArrangementService.autoAssignSeats(examIds, roomId));
//    }

}

