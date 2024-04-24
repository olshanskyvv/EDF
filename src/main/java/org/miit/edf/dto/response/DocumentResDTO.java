package org.miit.edf.dto.response;

import lombok.Data;
import org.miit.edf.models.Document;
import org.miit.edf.models.DocumentType;

import java.time.LocalDateTime;

@Data
public class DocumentResDTO {
    private Long id;
    private String filename;
    private String path;
    private DocumentType type;
    private LocalDateTime createdAt;
    private LocalDateTime viewedAt;
    private UserDTO sender;
    private UserDTO recipient;
    public DocumentResDTO(Document document) {
        this.id = document.getId();
        this.filename = document.getFilename();
        this.path = document.getPath();
        this.type = document.getType();
        this.createdAt = document.getCreatedAt();
        this.viewedAt = document.getViewedAt();
        this.sender = new UserDTO(document.getSender());
        this.recipient = new UserDTO(document.getRecipient());
    }
}
