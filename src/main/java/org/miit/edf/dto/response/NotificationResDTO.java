package org.miit.edf.dto.response;

import lombok.Data;
import org.miit.edf.models.Notification;

@Data
public class NotificationResDTO {
    private Long id;
    private DocumentResDTO document;
    private String text;
    private String type;
    public NotificationResDTO(Notification notification) {
        this.id = notification.getId();
        this.document = new DocumentResDTO(notification.getDocument());
        this.text = notification.getText();
        this.type = notification.getType();
    }
}
