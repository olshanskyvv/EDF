package org.miit.edf.controllers;

import lombok.RequiredArgsConstructor;
import org.miit.edf.dto.request.DocumentReqDTO;
import org.miit.edf.dto.response.DocumentResDTO;
import org.miit.edf.services.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;
    @PostMapping("/send")
    public ResponseEntity<DocumentResDTO> sendDocument(DocumentReqDTO document) throws IOException {
        return ResponseEntity.ok(documentService.uploadDocument(document));
    }
}
