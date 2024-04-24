package org.miit.edf.controllers;

import lombok.RequiredArgsConstructor;
import org.miit.edf.dto.request.DocumentReqDTO;
import org.miit.edf.dto.response.DocumentResDTO;
import org.miit.edf.services.DocumentService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;
    @PostMapping("/send")
    public ResponseEntity<DocumentResDTO> sendDocument(DocumentReqDTO document) throws IOException {
        return ResponseEntity.ok(documentService.uploadDocument(document));
    }
    @GetMapping("/get/all/recipient")
    public ResponseEntity<List<DocumentResDTO>> getDocumentRecipient() {
        return ResponseEntity.ok(documentService.getAllDocumentsRecipient());
    }
    @GetMapping("/get/all/sender")
    public ResponseEntity<List<DocumentResDTO>> getDocumentSender() {
        return ResponseEntity.ok(documentService.getAllDocumentsSender());
    }
    @GetMapping("/get/{fileName}")
    public ResponseEntity<FileSystemResource> getDocument(@PathVariable String fileName) {
        FileSystemResource fileResource = new FileSystemResource(documentService.loadFile(fileName));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName);
        return ResponseEntity.ok()
                .headers(headers)
                .body(fileResource);
    }
}
