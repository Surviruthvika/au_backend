package com.anurag.examhall.service;

import com.anurag.examhall.dto.AuthDTOs.*;
import com.anurag.examhall.model.Admin;
import com.anurag.examhall.model.Student;
import com.anurag.examhall.repository.AdminRepository;
import com.anurag.examhall.repository.StudentRepository;
import com.anurag.examhall.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired private StudentRepository studentRepo;
    @Autowired private AdminRepository adminRepo;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtils jwtUtils;

    public MessageResponse studentSignup(StudentSignupRequest req) {
        if (studentRepo.existsByRollNumber(req.getRollNumber()))
            throw new RuntimeException("Roll number already registered");
        Student s = Student.builder()
            .rollNumber(req.getRollNumber())
            .name(req.getName())
            .branch(req.getBranch())
            .section(req.getSection())
            .phone(req.getPhone())
            .password(passwordEncoder.encode(req.getPassword()))
            .build();
        studentRepo.save(s);
        return new MessageResponse("Student registered successfully");
    }

    public AuthResponse studentLogin(StudentLoginRequest req) {
        Student s = studentRepo.findByRollNumber(req.getRollNumber())
            .orElseThrow(() -> new RuntimeException("Invalid roll number or password"));
        if (!passwordEncoder.matches(req.getPassword(), s.getPassword()))
            throw new RuntimeException("Invalid roll number or password");
        String token = jwtUtils.generateToken(s.getRollNumber(), "STUDENT");
        return new AuthResponse(token, "STUDENT", s.getName(), s.getRollNumber());
    }

    public MessageResponse adminSignup(AdminSignupRequest req) {
        if (adminRepo.existsByEmail(req.getEmail()))
            throw new RuntimeException("Email already registered");
        Admin a = Admin.builder()
            .name(req.getName())
            .email(req.getEmail())
            .password(passwordEncoder.encode(req.getPassword()))
            .build();
        adminRepo.save(a);
        return new MessageResponse("Admin registered successfully");
    }

    public AuthResponse adminLogin(AdminLoginRequest req) {
        Admin a = adminRepo.findByEmail(req.getEmail())
            .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        if (!passwordEncoder.matches(req.getPassword(), a.getPassword()))
            throw new RuntimeException("Invalid email or password");
        String token = jwtUtils.generateToken(a.getEmail(), "ADMIN");
        return new AuthResponse(token, "ADMIN", a.getName(), a.getEmail());
    }
}
