package org.miit.edf.dto.response;

import lombok.Data;
import org.miit.edf.models.DocumentType;

import java.time.LocalDate;

@Data
public class DocumentResDTO {
    private Long id;
    private String filename;
    private String path;
    private DocumentType type;
    private LocalDate createdAt;
    private LocalDate viewedAt;
    private UserDTO sender;
    private UserDTO recipient;
}
