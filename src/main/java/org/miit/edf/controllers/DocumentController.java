package org.miit.edf.controllers;

import lombok.RequiredArgsConstructor;
import org.miit.edf.services.DocumentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;
}
