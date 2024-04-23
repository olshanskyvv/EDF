package org.miit.edf.services;

import lombok.RequiredArgsConstructor;
import org.miit.edf.dto.response.NotificationResDTO;
import org.miit.edf.models.Document;
import org.miit.edf.models.Notification;
import org.miit.edf.models.User;
import org.miit.edf.repos.NotificationRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepo notificationRepo;
    public void addNotification(Document document) {
        Notification notification = new Notification();
        notification.setDocument(document);
        notification.setType("test type");
        notification.setText("test text");
        notification.setRecipient(document.getRecipient());
        notificationRepo.save(notification);
    }
    public List<NotificationResDTO> viewAllNotification(User user) {
        List<Notification> notifications = notificationRepo.findByRecipient(user);
        List<NotificationResDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {
            notificationDTOS.add(new NotificationResDTO(notification));
        }
        return notificationDTOS;
    }
}
