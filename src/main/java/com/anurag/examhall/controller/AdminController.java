package com.anurag.examhall.controller;

import com.anurag.examhall.dto.HallAllocationDTO;
import com.anurag.examhall.dto.NotificationDTO;
import com.anurag.examhall.model.Student;
import com.anurag.examhall.service.HallAllocationService;
import com.anurag.examhall.service.NotificationService;
import com.anurag.examhall.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired private StudentService studentService;
    @Autowired private HallAllocationService hallService;
    @Autowired private NotificationService notifService;

    @GetMapping("/students")
    public ResponseEntity<?> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/students/branch/{branch}")
    public ResponseEntity<?> getByBranch(@PathVariable Student.Branch branch) {
        return ResponseEntity.ok(studentService.getByBranch(branch));
    }

    @GetMapping("/students/branch/{branch}/section/{section}")
    public ResponseEntity<?> getByBranchAndSection(@PathVariable Student.Branch branch,
                                                    @PathVariable String section) {
        return ResponseEntity.ok(studentService.getByBranchAndSection(branch, section));
    }

    @PostMapping("/hall-allocations")
    public ResponseEntity<?> createAllocation(@RequestBody HallAllocationDTO dto) {
        return ResponseEntity.ok(hallService.createAllocation(dto));
    }

    @GetMapping("/hall-allocations")
    public ResponseEntity<?> getAllAllocations() {
        return ResponseEntity.ok(hallService.getAllAllocations());
    }

    @DeleteMapping("/hall-allocations/{id}")
    public ResponseEntity<?> deleteAllocation(@PathVariable Long id) {
        hallService.deleteAllocation(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    @PostMapping("/notifications")
    public ResponseEntity<?> sendNotification(@RequestBody NotificationDTO dto) {
        return ResponseEntity.ok(notifService.sendNotification(dto));
    }

    @GetMapping("/notifications")
    public ResponseEntity<?> getAllNotifications() {
        return ResponseEntity.ok(notifService.getAllNotifications());
    }
}
