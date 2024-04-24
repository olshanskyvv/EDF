package org.miit.edf.dto.request;

import lombok.Data;
import org.miit.edf.models.DocumentType;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DocumentReqDTO {
    MultipartFile file;
    DocumentType type;
    String description;
    String recipient;
}
