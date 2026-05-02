// NotificationRepository.java
package com.example.ems.repository;

import com.example.ems.model.Notification;
import com.example.ems.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserAndIsReadFalse(User user);
    List<Notification> findByUserOrderByCreatedAtDesc(User user);
}