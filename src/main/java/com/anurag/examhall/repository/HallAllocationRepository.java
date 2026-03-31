package com.anurag.examhall.repository;

import com.anurag.examhall.model.HallAllocation;
import com.anurag.examhall.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HallAllocationRepository extends JpaRepository<HallAllocation, Long> {
    List<HallAllocation> findByBranch(Student.Branch branch);
    List<HallAllocation> findByBranchAndSection(Student.Branch branch, String section);
}
