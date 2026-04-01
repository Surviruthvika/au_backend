package com.anurag.examhall.service;

import com.anurag.examhall.dto.HallAllocationDTO;
import com.anurag.examhall.model.HallAllocation;
import com.anurag.examhall.model.Student;
import com.anurag.examhall.repository.HallAllocationRepository;
import com.anurag.examhall.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HallAllocationService {

    private static final Logger logger = LoggerFactory.getLogger(HallAllocationService.class);

    @Autowired private HallAllocationRepository hallRepo;
    @Autowired private StudentRepository studentRepo;

    public HallAllocation createAllocation(HallAllocationDTO dto) {

        HallAllocation h = HallAllocation.builder()
            .branch(dto.getBranch())
            .section(dto.getSection())
            .rollNumberStart(dto.getRollNumberStart())
            .rollNumberEnd(dto.getRollNumberEnd())
            .hallName(dto.getHallName())
            .roomNumber(dto.getRoomNumber())
            .subject(dto.getSubject())
            .examDate(dto.getExamDate())
            .examTime(dto.getExamTime())
            .build();

        HallAllocation saved = hallRepo.save(h);

        List<Student> candidates =
            studentRepo.findByBranchAndSection(dto.getBranch(), dto.getSection());

        List<Student> targets = candidates.stream()
            .filter(s -> isRollInRange(
                s.getRollNumber(),
                dto.getRollNumberStart(),
                dto.getRollNumberEnd()))
            .toList();

        logger.info("Hall allocation created for {} student(s)", targets.size());

        // ✅ SMS DISABLED

        return saved;
    }

    public List<HallAllocation> getAllAllocations() {
        return hallRepo.findAll();
    }

    public List<HallAllocation> getByBranch(Student.Branch branch) {
        return hallRepo.findByBranch(branch);
    }

    public void deleteAllocation(Long id) {
        hallRepo.deleteById(id);
    }

    public List<HallAllocation> getAllocationsForStudent(String rollNumber) {
        Student s = studentRepo.findByRollNumber(rollNumber)
            .orElseThrow(() -> new RuntimeException("Student not found"));

        List<HallAllocation> all =
            hallRepo.findByBranchAndSection(s.getBranch(), s.getSection());

        return all.stream()
            .filter(h -> isRollInRange(
                rollNumber,
                h.getRollNumberStart(),
                h.getRollNumberEnd()))
            .toList();
    }

    private boolean isRollInRange(String roll, String start, String end) {
        try {
            int r = Integer.parseInt(roll.replaceAll("[^0-9]", ""));
            int s = Integer.parseInt(start.replaceAll("[^0-9]", ""));
            int e = Integer.parseInt(end.replaceAll("[^0-9]", ""));
            return r >= s && r <= e;
        } catch (Exception ex) {
            return roll.compareTo(start) >= 0 &&
                   roll.compareTo(end) <= 0;
        }
    }
}