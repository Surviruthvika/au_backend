package com.anurag.examhall.dto;

import com.anurag.examhall.model.Student;
import lombok.Data;
import java.time.LocalDate;

@Data
public class HallAllocationDTO {
    private Long id;
    private Student.Branch branch;
    private String section;
    private String rollNumberStart;
    private String rollNumberEnd;
    private String hallName;
    private String roomNumber;
    private String subject;
    private LocalDate examDate;
    private String examTime;
}
