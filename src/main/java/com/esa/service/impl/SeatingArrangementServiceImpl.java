package com.esa.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.esa.entity.Exam;
import com.esa.entity.Room;
import com.esa.entity.SeatingArrangement;
import com.esa.entity.Student;
import com.esa.exception.DuplicateSeatAssignmentException;
import com.esa.exception.ResourceNotFoundException;
import com.esa.model.SeatingArrangementDTO;
import com.esa.model.StudentDTO;
import com.esa.repository.ExamRepository;
import com.esa.repository.RoomRepository;
import com.esa.repository.SeatingArrangementRepository;
import com.esa.repository.StudentRepository;
import com.esa.service.ExamService;
import com.esa.service.RoomService;
import com.esa.service.SeatingArrangementService;
import com.esa.service.StudentService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.apache.poi.ss.usermodel.*;



/**
 * Implementation of SeatingArrangementService.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SeatingArrangementServiceImpl implements SeatingArrangementService {

    private final SeatingArrangementRepository seatingArrangementRepository;
    private final ExamRepository examRepository;
    private final RoomRepository roomRepository;
    private final StudentRepository studentRepository;
    
    @Autowired
    private StudentService studentService;

    @Autowired
    private ExamService examService;

    @Autowired
    private RoomService roomService;

    /**
     * Assign a seat to a student in a specific exam and room.
     * 
     * @param examId the ID of the exam
     * @param roomId the ID of the room
     * @param studentId the ID of the student
     * @return the created seating arrangement DTO
     */
//    @Override
//    public SeatingArrangementDTO assignSeat(Long examId, Long roomId, Long studentId) {
//        // Validate existence of exam, room, and student
//        Exam exam = examRepository.findById(examId)
//                .orElseThrow(() -> new ResourceNotFoundException("Exam not found with ID: " + examId));
//        Room room = roomRepository.findById(roomId)
//                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + roomId));
//        Student student = studentRepository.findById(studentId)
//                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + studentId));
//
//        // Create the seating arrangement
//        SeatingArrangement seatingArrangement = new SeatingArrangement();
//        seatingArrangement.setExam(exam);
//        seatingArrangement.setRoom(room);
//        seatingArrangement.setStudent(student);
//
//        // Save the seating arrangement
//        SeatingArrangement savedSeatingArrangement = seatingArrangementRepository.save(seatingArrangement);
//
//        // Return the DTO
//        return mapToDTO(savedSeatingArrangement);
//    }

    /**
     * Get the seating arrangements for a specific exam.
     * 
     * @param examId the ID of the exam
     * @return the list of seating arrangement DTOs
     */
    @Override
    public List<SeatingArrangementDTO> getSeatingByExam(Long examId) {
        List<SeatingArrangement> seatingArrangements = seatingArrangementRepository.findByExamId(examId);
        return seatingArrangements.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get the seating arrangements for a specific room.
     * 
     * @param roomId the ID of the room
     * @return the list of seating arrangement DTOs
     */
    @Override
    public List<SeatingArrangementDTO> getSeatingByRoom(Long roomId) {
        List<SeatingArrangement> seatingArrangements = seatingArrangementRepository.findByRoomId(roomId);
        return seatingArrangements.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get the seating arrangements for a specific student.
     * 
     * @param studentId the ID of the student
     * @return the list of seating arrangement DTOs
     */
    @Override
    public List<SeatingArrangementDTO> getSeatingByStudent(Long studentId) {
        List<SeatingArrangement> seatingArrangements = seatingArrangementRepository.findByStudentId(studentId);
        return seatingArrangements.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Maps SeatingArrangement entity to DTO.
     * 
     * @param seatingArrangement the seating arrangement entity
     * @return the seating arrangement DTO
     */
    private SeatingArrangementDTO mapToDTO(SeatingArrangement seatingArrangement) {
        SeatingArrangementDTO dto = new SeatingArrangementDTO();
        dto.setId(seatingArrangement.getId());
        dto.setExamId(seatingArrangement.getExam().getId());
        dto.setRoomId(seatingArrangement.getRoom().getId());
        dto.setStudentId(seatingArrangement.getStudent().getId());
        dto.setSeatNumber(seatingArrangement.getSeatNumber());
        dto.setPin(seatingArrangement.getStudent().getPin());
        return dto;
    }
    
    @Override
    public List<StudentDTO> getStudentsByRoomAndExam(Long roomId, Long examId) {
        List<SeatingArrangement> arrangements = seatingArrangementRepository.findByRoomIdAndExamId(roomId, examId);
        return arrangements.stream()
                .map(arrangement -> {
                    Student student = arrangement.getStudent(); // Fetch the student directly
                    return new StudentDTO(
                            student.getId(),
                            student.getPin(),
                            student.getName(),
                            student.getBranch(),
                            student.getSemester(),
                            student.getCollegeCode(),
                            student.getYear(),
                            student.getDob()
                    );
                })
                .collect(Collectors.toList());
    }
    
    
    public Map<String, Object> getRoomExamSummary(Long roomId, Long examId) {
        List<SeatingArrangement> arrangements = seatingArrangementRepository.findByRoomIdAndExamId(roomId, examId);
        long totalStudents = arrangements.size();
        long availableSeats = roomRepository.findById(roomId).orElseThrow().getCapacity() - totalStudents;

        Map<String, Object> summary = new HashMap<>();
        summary.put("roomId", roomId);
        summary.put("examId", examId);
        summary.put("totalStudents", totalStudents);
        summary.put("availableSeats", availableSeats);
        return summary;
    }
    
    public Page<StudentDTO> getStudentsByRoomAndExamPaginated(Long roomId, Long examId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SeatingArrangement> arrangements = seatingArrangementRepository.findByRoomIdAndExamId(roomId, examId, pageable);

        return arrangements.map(arrangement -> {
            Student student = arrangement.getStudent();
            return new StudentDTO(
                student.getId(),
                student.getPin(),
                student.getName(),
                student.getBranch(),
                student.getSemester(),
                student.getCollegeCode(),
                student.getYear(),
                student.getDob()
            );
        });
    }

    
    public List<StudentDTO> searchStudents(Long examId, String name, String pin) {
        List<SeatingArrangement> arrangements = seatingArrangementRepository.findByExamId(examId);
        return arrangements.stream()
            .filter(arrangement -> {
                Student student = arrangement.getStudent();
                return (name == null || student.getName().toLowerCase().contains(name.toLowerCase())) &&
                       (pin == null || student.getPin().equalsIgnoreCase(pin));
            })
            .map(arrangement -> {
                Student student = arrangement.getStudent();
                return new StudentDTO(
                    student.getId(),
                    student.getPin(),
                    student.getName(),
                    student.getBranch(),
                    student.getSemester(),
                    student.getCollegeCode(),
                    student.getYear(),
                    student.getDob()
                );
            })
            .collect(Collectors.toList());
    }

    
  
    @Override
    public SeatingArrangementDTO assignSeat(Long examId, Long roomId, Long studentId) {
        // Validate existence of exam, room, and student
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found with ID: " + examId));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + roomId));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + studentId));

        // Check if the student is already assigned a seat in this exam
        List<SeatingArrangement> existingArrangements = seatingArrangementRepository.findByExamIdAndStudentId(examId, studentId);
        if (!existingArrangements.isEmpty()) {
            throw new DuplicateSeatAssignmentException("Student with ID " + studentId + " is already assigned a seat for Exam ID " + examId);
        }


        // Get the next available random seat number
        int seatNumber = getRandomAvailableSeat(room);

        System.out.println("Assigning seat number: " + seatNumber);

        // Create the seating arrangement
        SeatingArrangement seatingArrangement = new SeatingArrangement();
        seatingArrangement.setExam(exam);
        seatingArrangement.setRoom(room);
        seatingArrangement.setStudent(student);
        seatingArrangement.setSeatNumber(seatNumber); // Assign the seat number

        // Save the seating arrangement
        SeatingArrangement savedSeatingArrangement = seatingArrangementRepository.save(seatingArrangement);

        // Return the DTO
        return mapToDTO(savedSeatingArrangement);
    }

    // Helper method to get a random available seat number
    private int getRandomAvailableSeat(Room room) {
        // Fetch all seating arrangements for the room
        Set<Integer> assignedSeats = new HashSet<>();
        List<SeatingArrangement> existingArrangements = seatingArrangementRepository.findByRoomId(room.getId());
        
        // Collect the seat numbers that are already assigned
        for (SeatingArrangement arrangement : existingArrangements) {
            assignedSeats.add(arrangement.getSeatNumber());
        }

        // Generate a random seat number until we find an available one
        Random random = new Random();
        int seatNumber;
        do {
            seatNumber = random.nextInt(room.getCapacity()) + 1; // Random seat number between 1 and capacity
        } while (assignedSeats.contains(seatNumber)); // Ensure seat is not already taken

        return seatNumber;
    }
    
    
    /**
     * Export seating arrangements to an Excel file for a specific room and exam.
     *
     * @param roomId the ID of the room
     * @param examId the ID of the exam
     * @param response HttpServletResponse to write the Excel file
     */
    public void exportSeatingArrangementsToExcel(Long roomId, Long examId, HttpServletResponse response) throws IOException {
        List<SeatingArrangement> arrangements = seatingArrangementRepository.findByRoomIdAndExamId(roomId, examId);

        // Set up the Excel file
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Seating Arrangements");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Student PIN", "Student Name", "Branch", "Semester", "Year", "Exam Code", "Exam Date", "Seat Number", "Room Number"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(createHeaderStyle(workbook));
        }

        // Populate data rows
        int rowIndex = 1;
        for (SeatingArrangement arrangement : arrangements) {
            Row row = sheet.createRow(rowIndex++);
            Student student = arrangement.getStudent();
            Exam exam = arrangement.getExam();
            Room room = arrangement.getRoom();

            row.createCell(0).setCellValue(student.getPin());
            row.createCell(1).setCellValue(student.getName());
            row.createCell(2).setCellValue(student.getBranch());
            row.createCell(3).setCellValue(student.getSemester());
            row.createCell(4).setCellValue(student.getYear());
            row.createCell(5).setCellValue(exam.getSubjectCode());
            row.createCell(6).setCellValue(exam.getExamDate().toString());
            row.createCell(7).setCellValue(arrangement.getSeatNumber());
            row.createCell(8).setCellValue(room.getRoomNumber());
        }

        // Auto-size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write to response
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=seating_arrangements.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    // Helper method to create a header style
    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    
    @Override
    public void exportToExcel(HttpServletResponse response, Long roomId, Long examId) throws IOException {
        // Fetch seating arrangement data for the given roomId and examId
        List<SeatingArrangement> seatingArrangements = seatingArrangementRepository.findByRoomIdAndExamId(roomId, examId);

        // Create Excel workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Seating Arrangements");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Student PIN", "Student Name", "Room Number", "Seat Number"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            cell.setCellStyle(style);
        }

        // Populate data rows
        int rowCount = 1;
        for (SeatingArrangement arrangement : seatingArrangements) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(arrangement.getStudent().getPin());
            row.createCell(1).setCellValue(arrangement.getStudent().getName());
            row.createCell(2).setCellValue(arrangement.getRoom().getRoomNumber());
            row.createCell(3).setCellValue(arrangement.getSeatNumber());
        }

        // Auto-size columns for better readability
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the Excel file to the response output stream
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private StudentDTO mapStudentToDTO(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getPin(),
                student.getName(),
                student.getBranch(),
                student.getSemester(),
                student.getCollegeCode(),
                student.getYear(),
                student.getDob()
        );
    }

    @Override
    public List<SeatingArrangementDTO> autoAssignSeats(List<Long> examIds, Long roomId) throws Exception {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + roomId));
        int roomCapacity = room.getCapacity();
        List<SeatingArrangement> newArrangements = new ArrayList<>();
        List<Student> allEligibleStudents = new ArrayList<>();
        List<SeatingArrangement> existingArrangementsInRoom = seatingArrangementRepository.findByRoomId(roomId);
        int nextAvailableSeat = existingArrangementsInRoom.size() + 1;

        for (Long examId : examIds) {
            Exam exam = examRepository.findById(examId)
                    .orElseThrow(() -> new ResourceNotFoundException("Exam not found with ID: " + examId));
            List<Student> studentsForExamSemester = studentRepository.findBySemesterAndBranch(exam.getSemester(), exam.getBranch()); // Assuming you have this repository method
            allEligibleStudents.addAll(studentsForExamSemester);
        }

        if (allEligibleStudents.size() > roomCapacity - existingArrangementsInRoom.size()) {
            throw new IllegalStateException("Number of students exceeds the remaining capacity of the room.");
        }

        // Basic random assignment logic
        List<Student> shuffledStudents = new ArrayList<>(allEligibleStudents);
        java.util.Collections.shuffle(shuffledStudents, new Random());

        for (Student student : shuffledStudents) {
            if (nextAvailableSeat <= roomCapacity) {
                // Assign the student to the current room for all the selected exams
                for (Long examId : examIds) {
                    Exam exam = examRepository.findById(examId)
                            .orElseThrow(() -> new ResourceNotFoundException("Exam not found with ID: " + examId));
                    if (seatingArrangementRepository.findByExamIdAndStudentId(examId, student.getId()).isEmpty()) {
                        SeatingArrangement arrangement = new SeatingArrangement();
                        arrangement.setExam(exam);
                        arrangement.setRoom(room);
                        arrangement.setStudent(student);
                        arrangement.setSeatNumber(nextAvailableSeat);
                        newArrangements.add(arrangement);
                    }
                }
                nextAvailableSeat++;
            } else {
                break; // Room capacity reached
            }
        }

        return seatingArrangementRepository.saveAll(newArrangements).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

//    @Override
//    public List<SeatingArrangementDTO> autoAssignSeats(Long examId, Long roomId) throws Exception {
//        return autoAssignSeats(List.of(examId), roomId);
//    }
    
}
