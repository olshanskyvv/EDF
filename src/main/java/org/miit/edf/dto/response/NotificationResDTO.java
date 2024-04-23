package org.miit.edf.dto.response;

import lombok.Data;

@Data
public class NotificationResDTO {
    private Long id;
    private DocumentResDTO document;
    private String text;
    private String type;
}
