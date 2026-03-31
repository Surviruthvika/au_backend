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
    @Autowired private TwilioSmsService smsService;

    /**
     * Creates a hall allocation and automatically sends an SMS to every
     * student whose roll number falls within the specified range.
     * The SMS includes: subject, hall name, room number, date and time.
     */
    public HallAllocation createAllocation(HallAllocationDTO dto) {
        // 1. Save the allocation
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

        // 2. Find all students in that branch + section
        List<Student> candidates = studentRepo.findByBranchAndSection(dto.getBranch(), dto.getSection());

        // 3. Filter to only those in the roll number range
        List<Student> targets = candidates.stream()
            .filter(s -> isRollInRange(s.getRollNumber(), dto.getRollNumberStart(), dto.getRollNumberEnd()))
            .toList();

        logger.info("Sending exam hall SMS to {} student(s) for subject: {}", targets.size(), dto.getSubject());

        // 4. Send personalised SMS to each matched student
        for (Student student : targets) {
            String sms = buildSmsMessage(student, saved);
            smsService.sendSms(student.getPhone(), sms);
        }

        return saved;
    }

    /**
     * Builds a clear, informative SMS message for the student.
     * Example:
     *   [AU Exam Hall] Hi Ravi (22B01A0501),
     *   Your exam details:
     *   Subject  : Data Structures
     *   Hall     : Block A
     *   Room No. : 101
     *   Date     : 2025-04-10
     *   Time     : 10:00 AM
     *   - Anurag University
     */
    private String buildSmsMessage(Student student, HallAllocation h) {
        return String.format(
            "[AU Exam Hall] Hi %s (%s),\n" +
            "Your exam details:\n" +
            "Subject  : %s\n" +
            "Hall     : %s\n" +
            "Room No. : %s\n" +
            "Date     : %s\n" +
            "Time     : %s\n" +
            "- Anurag University",
            student.getName(),
            student.getRollNumber(),
            h.getSubject(),
            h.getHallName(),
            h.getRoomNumber(),
            h.getExamDate().toString(),
            h.getExamTime()
        );
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
        List<HallAllocation> all = hallRepo.findByBranchAndSection(s.getBranch(), s.getSection());
        return all.stream()
            .filter(h -> isRollInRange(rollNumber, h.getRollNumberStart(), h.getRollNumberEnd()))
            .toList();
    }

    private boolean isRollInRange(String roll, String start, String end) {
        try {
            String numPart  = roll.replaceAll("[^0-9]", "");
            String startNum = start.replaceAll("[^0-9]", "");
            String endNum   = end.replaceAll("[^0-9]", "");
            int r = Integer.parseInt(numPart);
            int s = Integer.parseInt(startNum);
            int e = Integer.parseInt(endNum);
            return r >= s && r <= e;
        } catch (NumberFormatException ex) {
            // Fallback: lexicographic comparison
            return roll.compareTo(start) >= 0 && roll.compareTo(end) <= 0;
        }
    }
}
