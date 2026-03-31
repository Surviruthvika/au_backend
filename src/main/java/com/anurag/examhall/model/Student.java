package com.anurag.examhall.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String rollNumber;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Branch branch;

    @Column(nullable = false)
    private String section;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String password;

    public enum Branch {
        CSE, ECE, EEE, MECH, CIVIL
    }
}
