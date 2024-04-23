package org.miit.edf.repos;

import org.miit.edf.models.Notification;
import org.miit.edf.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipient(User user);
}
