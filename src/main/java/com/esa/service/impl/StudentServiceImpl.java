package com.esa.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esa.entity.Student;
import com.esa.exception.ResourceNotFoundException;
import com.esa.model.StudentDTO;
import com.esa.repository.StudentRepository;
import com.esa.service.StudentService;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of StudentService.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository ;
    
    private static final List<String> VALID_DEPARTMENTS = Arrays.asList("C", "M", "CM", "EC", "EE");

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        validatePin(studentDTO.getPin());
        Student student = mapToEntity(studentDTO);
        Student savedStudent = studentRepository.save(student);
        return mapToDTO(savedStudent);
    }

    @Override
    public StudentDTO getStudentByPin(String pin) {
        Student student = studentRepository.findByPin(pin)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with PIN: " + pin));
        return mapToDTO(student);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public StudentDTO updateStudent(String pin, StudentDTO studentDTO) {
        Student student = studentRepository.findByPin(pin)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with PIN: " + pin));
        student.setName(studentDTO.getName());
        student.setBranch(studentDTO.getBranch());
        student.setSemester(studentDTO.getSemester());
        student.setCollegeCode(studentDTO.getCollegeCode());
        student.setYear(studentDTO.getYear());
        student.setDob(studentDTO.getDob());
        return mapToDTO(studentRepository.save(student));
    }

    @Override
    public void deleteStudent(String pin) {
        Student student = studentRepository.findByPin(pin)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with PIN: " + pin));
        studentRepository.delete(student);
    }

    private void validatePin(String pin) {
        String[] parts = pin.split("-");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid PIN format. Expected format: 22022-CM-045");
        }

        String departmentCode = parts[1];
        if (!VALID_DEPARTMENTS.contains(departmentCode)) {
            throw new IllegalArgumentException("Invalid department code in PIN.");
        }

        if (!parts[0].matches("\\d{5}") || !parts[2].matches("\\d{3}")) {
            throw new IllegalArgumentException("Invalid PIN format. Expected format: 22022-CM-045");
        }
    }

    private Student mapToEntity(StudentDTO studentDTO) {
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setPin(studentDTO.getPin());
        student.setName(studentDTO.getName());
        student.setBranch(studentDTO.getBranch());
        student.setSemester(studentDTO.getSemester());
        student.setCollegeCode(studentDTO.getCollegeCode());
        student.setYear(studentDTO.getYear());
        student.setDob(studentDTO.getDob());
        return student;
    }

    private StudentDTO mapToDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setPin(student.getPin());
        studentDTO.setName(student.getName());
        studentDTO.setBranch(student.getBranch());
        studentDTO.setSemester(student.getSemester());
        studentDTO.setCollegeCode(student.getCollegeCode());
        studentDTO.setYear(student.getYear());
        studentDTO.setDob(student.getDob());
        return studentDTO;
    }
    
    public boolean isPinExists(String pin) {
        return studentRepository.existsByPin(pin);
    }
}
