package com.anurag.examhall.controller;

import com.anurag.examhall.dto.AuthDTOs.*;
import com.anurag.examhall.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthService authService;

    @PostMapping("/student/signup")
    public ResponseEntity<?> studentSignup(@RequestBody StudentSignupRequest req) {
        return ResponseEntity.ok(authService.studentSignup(req));
    }

    @PostMapping("/student/login")
    public ResponseEntity<?> studentLogin(@RequestBody StudentLoginRequest req) {
        return ResponseEntity.ok(authService.studentLogin(req));
    }

    @PostMapping("/admin/signup")
    public ResponseEntity<?> adminSignup(@RequestBody AdminSignupRequest req) {
        return ResponseEntity.ok(authService.adminSignup(req));
    }

    @PostMapping("/admin/login")
    public ResponseEntity<?> adminLogin(@RequestBody AdminLoginRequest req) {
        return ResponseEntity.ok(authService.adminLogin(req));
    }
}
