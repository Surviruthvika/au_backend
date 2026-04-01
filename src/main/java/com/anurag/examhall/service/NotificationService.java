package com.anurag.examhall.service;

import com.anurag.examhall.dto.NotificationDTO;
import com.anurag.examhall.model.HallAllocation;
import com.anurag.examhall.model.Notification;
import com.anurag.examhall.model.Student;
import com.anurag.examhall.repository.HallAllocationRepository;
import com.anurag.examhall.repository.NotificationRepository;
import com.anurag.examhall.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired private NotificationRepository notifRepo;
    @Autowired private StudentRepository studentRepo;
    @Autowired private HallAllocationRepository hallRepo;

    public Notification sendNotification(NotificationDTO dto) {

        String target;
        Notification.NotificationType type;

        if (dto.getType() == Notification.NotificationType.ALL_BRANCH) {
            target = "ALL";
            type = Notification.NotificationType.ALL_BRANCH;
        } else if (dto.getType() == Notification.NotificationType.BRANCH_SPECIFIC) {
            target = dto.getBranch();
            type = Notification.NotificationType.BRANCH_SPECIFIC;
        } else {
            target = String.join(",", dto.getRollNumbers());
            type = Notification.NotificationType.SPECIFIC_STUDENTS;
        }

        Notification n = Notification.builder()
            .message(dto.getMessage())
            .date(dto.getDate() != null ? dto.getDate() : LocalDate.now())
            .time(dto.getTime() != null ? dto.getTime() : LocalTime.now())
            .target(target)
            .type(type)
            .build();

        notifRepo.save(n);

        // ✅ SMS DISABLED

        return n;
    }

    private String buildEnrichedSms(Student student, String adminMessage) {
        StringBuilder sb = new StringBuilder();

        sb.append("[AU Notice] Hi ").append(student.getName()).append(",\n");
        sb.append(adminMessage).append("\n");

        List<HallAllocation> allocs =
            hallRepo.findByBranchAndSection(student.getBranch(), student.getSection());

        List<HallAllocation> mine = allocs.stream()
            .filter(h -> isRollInRange(
                student.getRollNumber(),
                h.getRollNumberStart(),
                h.getRollNumberEnd()))
            .toList();

        if (!mine.isEmpty()) {
            sb.append("\nYour Exam Details:");
            for (HallAllocation h : mine) {
                sb.append("\nSubject  : ").append(h.getSubject());
                sb.append("\nHall     : ").append(h.getHallName());
                sb.append("\nRoom No. : ").append(h.getRoomNumber());
                sb.append("\nDate     : ").append(h.getExamDate());
                sb.append("\nTime     : ").append(h.getExamTime());
            }
        }

        sb.append("\n- Anurag University");
        return sb.toString();
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

    public List<Notification> getAllNotifications() {
        return notifRepo.findAll();
    }

    public List<Notification> getNotificationsForStudent(String rollNumber) {
        Student s = studentRepo.findByRollNumber(rollNumber)
            .orElseThrow(() -> new RuntimeException("Student not found"));

        return notifRepo.findNotificationsForStudent(
            rollNumber,
            s.getBranch().name());
    }
}