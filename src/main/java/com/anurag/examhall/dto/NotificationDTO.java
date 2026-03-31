package com.anurag.examhall.dto;

import com.anurag.examhall.model.Notification;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class NotificationDTO {
    private Long id;
    private String message;
    private LocalDate date;
    private LocalTime time;
    private String target;
    private Notification.NotificationType type;
    private List<String> rollNumbers; // used when type = SPECIFIC_STUDENTS
    private String branch;            // used when type = BRANCH_SPECIFIC
    private boolean sendSms;
}
