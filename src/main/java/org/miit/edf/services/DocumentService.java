package org.miit.edf.services;

import lombok.RequiredArgsConstructor;
import org.miit.edf.dto.request.DocumentReqDTO;
import org.miit.edf.dto.response.DocumentResDTO;
import org.miit.edf.models.Document;
import org.miit.edf.models.User;
import org.miit.edf.repos.DocumentRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepo documentRepo;
    private final String path = "./src/main/resources/files/";

    public String saveFile(MultipartFile file) throws IOException {
        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + file.getOriginalFilename();
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        file.transferTo(new File(path + resultFilename));
        return path + resultFilename;
    }

    public DocumentResDTO uploadDocument(DocumentReqDTO document) throws IOException {
        Document documentForBD = new Document();
        documentForBD.setType(document.getType());
        String path = saveFile(document.getFile());
        documentForBD.setFilename(document.getFile().getOriginalFilename());
        documentForBD.setPath(path);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User sender = (User) authentication.getPrincipal();
        documentForBD.setSender(sender);
        documentForBD.setRecipient(document.getRecipient());
        documentRepo.save(documentForBD);
    }
}
