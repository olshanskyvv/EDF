package org.miit.edf.services;

import lombok.RequiredArgsConstructor;
import org.miit.edf.repos.DocumentRepo;
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

    public void createDocument() {

    }
}
