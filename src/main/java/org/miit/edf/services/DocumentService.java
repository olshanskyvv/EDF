package org.miit.edf.services;

import lombok.RequiredArgsConstructor;
import org.miit.edf.dto.request.DocumentReqDTO;
import org.miit.edf.dto.response.DocumentResDTO;
import org.miit.edf.models.Document;
import org.miit.edf.models.User;
import org.miit.edf.repos.DocumentRepo;
import org.miit.edf.repos.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepo documentRepo;
    private final NotificationService notificationService;
    private final UserRepo userRepo;
    @Value("${doc.path}")
    private String path;
    public String saveFile(MultipartFile file) throws IOException {
        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + file.getOriginalFilename();
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        file.transferTo(new File(path + "/" + resultFilename));
        return resultFilename;
    }
    public DocumentResDTO uploadDocument(DocumentReqDTO document) throws IOException {
        Document documentForBD = new Document();
        documentForBD.setType(document.getType());
        String path = saveFile(document.getFile());
        documentForBD.setFilename(document.getFile().getOriginalFilename());
        documentForBD.setPath(path);
        documentForBD.setDescription(document.getDescription());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User sender = (User) authentication.getPrincipal();
        documentForBD.setSender(sender);
        documentForBD.setRecipient(userRepo.findByLogin(document.getRecipient()));
        notificationService.addNotification(documentForBD);
        return new DocumentResDTO(documentRepo.saveAndFlush(documentForBD));
    }
    public List<DocumentResDTO> getAllDocuments() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Document> documents = documentRepo.findByRecipient_Login(authentication.getName());
        if (documents.isEmpty()) {
            return List.of();
        }
        return documents.stream().map(DocumentResDTO::new).toList();
    }
    public String loadFile(String fileNme) {
        return path+"/"+fileNme;
    }
}
