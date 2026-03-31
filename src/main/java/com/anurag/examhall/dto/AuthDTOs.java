package com.anurag.examhall.dto;

import com.anurag.examhall.model.Student;
import lombok.Data;

public class AuthDTOs {

    @Data
    public static class StudentSignupRequest {
        private String rollNumber;
        private String name;
        private Student.Branch branch;
        private String section;
        private String phone;
        private String password;
    }

    @Data
    public static class StudentLoginRequest {
        private String rollNumber;
        private String password;
    }

    @Data
    public static class AdminSignupRequest {
        private String name;
        private String email;
        private String password;
    }

    @Data
    public static class AdminLoginRequest {
        private String email;
        private String password;
    }

    @Data
    public static class AuthResponse {
        private String token;
        private String role;
        private String name;
        private String identifier; // email for admin, rollNumber for student

        public AuthResponse(String token, String role, String name, String identifier) {
            this.token = token;
            this.role = role;
            this.name = name;
            this.identifier = identifier;
        }
    }

    @Data
    public static class MessageResponse {
        private String message;
        public MessageResponse(String message) {
            this.message = message;
        }
    }
}
