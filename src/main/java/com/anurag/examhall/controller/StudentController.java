package com.anurag.examhall.controller;

import com.anurag.examhall.service.HallAllocationService;
import com.anurag.examhall.service.NotificationService;
import com.anurag.examhall.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired private StudentService studentService;
    @Autowired private HallAllocationService hallService;
    @Autowired private NotificationService notifService;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication auth) {
        return ResponseEntity.ok(studentService.getByRollNumber(auth.getName()));
    }

    @GetMapping("/hall-allocations")
    public ResponseEntity<?> getMyAllocations(Authentication auth) {
        return ResponseEntity.ok(hallService.getAllocationsForStudent(auth.getName()));
    }

    @GetMapping("/notifications")
    public ResponseEntity<?> getMyNotifications(Authentication auth) {
        return ResponseEntity.ok(notifService.getNotificationsForStudent(auth.getName()));
    }
}
