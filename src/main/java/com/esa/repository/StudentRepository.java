package com.esa.repository;

import com.esa.entity.Student;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Student entity.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByPin(String pin); // Custom query to find a student by PIN
    boolean existsByPin(String pin);
	
    List<Student> findBySemesterAndBranch(int semester, String branch);
	
}
