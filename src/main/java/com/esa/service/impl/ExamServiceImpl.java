package com.esa.service.impl;

import com.esa.entity.Exam;
import com.esa.exception.ResourceNotFoundException;
import com.esa.model.ExamDTO;
import com.esa.repository.ExamRepository;
import com.esa.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for Exam operations.
 */
@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;

    @Override
    public ExamDTO createExam(ExamDTO examDTO) {
        Exam exam = mapToEntity(examDTO);
        Exam savedExam = examRepository.save(exam);
        return mapToDTO(savedExam);
    }

    @Override
    public ExamDTO getExamById(Long id) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found with ID: " + id));
        return mapToDTO(exam);
    }

    @Override
    public List<ExamDTO> getAllExams() {
        List<Exam> exams = examRepository.findAll();
        return exams.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ExamDTO updateExam(Long id, ExamDTO examDTO) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found with ID: " + id));
        exam.setExamDate(examDTO.getExamDate());
        exam.setSemester(examDTO.getSemester());
        exam.setBranch(examDTO.getBranch());
        exam.setSubjectCode(examDTO.getSubjectCode());
        exam.setYear(examDTO.getYear());
        return mapToDTO(examRepository.save(exam));
    }

    @Override
    public void deleteExam(Long id) {
        if (!examRepository.existsById(id)) {
            throw new ResourceNotFoundException("Exam not found with ID: " + id);
        }
        examRepository.deleteById(id);
    }

    private Exam mapToEntity(ExamDTO examDTO) {
        return Exam.builder()
                .examDate(examDTO.getExamDate())
                .semester(examDTO.getSemester())
                .branch(examDTO.getBranch())
                .subjectCode(examDTO.getSubjectCode())
                .year(examDTO.getYear())
                .build();
    }

    private ExamDTO mapToDTO(Exam exam) {
        ExamDTO examDTO = new ExamDTO();
        examDTO.setId(exam.getId());
        examDTO.setExamDate(exam.getExamDate());
        examDTO.setSemester(exam.getSemester());
        examDTO.setBranch(exam.getBranch());
        examDTO.setSubjectCode(exam.getSubjectCode());
        examDTO.setYear(exam.getYear());
        return examDTO;
    }
}
