package com.anurag.examhall.repository;

import com.anurag.examhall.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.type = 'ALL_BRANCH' OR n.target = :rollNumber OR n.target LIKE %:rollNumber% OR n.target = :branch")
    List<Notification> findNotificationsForStudent(@Param("rollNumber") String rollNumber, @Param("branch") String branch);

    List<Notification> findByType(Notification.NotificationType type);
}
