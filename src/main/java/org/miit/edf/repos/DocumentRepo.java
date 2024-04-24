package org.miit.edf.repos;

import org.miit.edf.models.Document;
import org.miit.edf.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepo extends JpaRepository<Document, Long> {
    List<Document> findByRecipient_Login(String user);
    List<Document> findBySender_Login(String user);
}
