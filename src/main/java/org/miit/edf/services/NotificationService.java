package org.miit.edf.services;

import lombok.RequiredArgsConstructor;
import org.miit.edf.dto.response.NotificationResDTO;
import org.miit.edf.models.Document;
import org.miit.edf.models.Notification;
import org.miit.edf.models.User;
import org.miit.edf.repos.DocumentRepo;
import org.miit.edf.repos.NotificationRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepo notificationRepo;
    private final DocumentRepo documentRepo;
    public void addNotification(Document document) {
        Notification notification = new Notification();
        notification.setDocument(document);
        notification.setType("test type");
        notification.setText("test text");
        notification.setRecipient(document.getRecipient());
        notificationRepo.save(notification);
    }
    public List<NotificationResDTO> showAllNotification(User user) {
        List<Notification> notifications = notificationRepo.findByRecipient(user);
        List<NotificationResDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {
            notificationDTOS.add(new NotificationResDTO(notification));
        }
        return notificationDTOS;
    }
    public void viewNotification(Long id) {
        Notification notification = notificationRepo.findById(id).orElseThrow();
        Document document = documentRepo.findById(notification.getDocument().getId()).orElseThrow();
        document.setViewedAt(LocalDateTime.now());
        notificationRepo.delete(notification);
    }
}
