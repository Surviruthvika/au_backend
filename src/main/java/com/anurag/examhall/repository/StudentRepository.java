package com.anurag.examhall.repository;

import com.anurag.examhall.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByRollNumber(String rollNumber);
    boolean existsByRollNumber(String rollNumber);
    List<Student> findByBranch(Student.Branch branch);
    List<Student> findByBranchAndSection(Student.Branch branch, String section);
    List<Student> findByRollNumberIn(List<String> rollNumbers);
}
