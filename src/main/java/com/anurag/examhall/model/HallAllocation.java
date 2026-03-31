package com.anurag.examhall.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "hall_allocations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HallAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Student.Branch branch;

    @Column(nullable = false)
    private String section;

    @Column(nullable = false)
    private String rollNumberStart;

    @Column(nullable = false)
    private String rollNumberEnd;

    @Column(nullable = false)
    private String hallName;

    @Column(nullable = false)
    private String roomNumber;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private LocalDate examDate;

    @Column(nullable = false)
    private String examTime;
}
