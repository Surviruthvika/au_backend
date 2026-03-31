package com.anurag.examhall.service;

import com.anurag.examhall.model.Student;
import com.anurag.examhall.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {

    @Autowired private StudentRepository studentRepo;

    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    public List<Student> getByBranch(Student.Branch branch) {
        return studentRepo.findByBranch(branch);
    }

    public List<Student> getByBranchAndSection(Student.Branch branch, String section) {
        return studentRepo.findByBranchAndSection(branch, section);
    }

    public Student getByRollNumber(String rollNumber) {
        return studentRepo.findByRollNumber(rollNumber)
            .orElseThrow(() -> new RuntimeException("Student not found"));
    }
}
