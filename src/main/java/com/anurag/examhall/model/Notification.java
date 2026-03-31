package com.anurag.examhall.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String message;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    // Target: "ALL", branch name, or comma-separated roll numbers
    @Column(nullable = false)
    private String target;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    public enum NotificationType {
        ALL_BRANCH, SPECIFIC_STUDENTS, BRANCH_SPECIFIC
    }
}
