package org.miit.edf.controllers;

import lombok.RequiredArgsConstructor;
import org.miit.edf.dto.response.NotificationResDTO;
import org.miit.edf.models.User;
import org.miit.edf.services.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    @GetMapping("/show")
    public ResponseEntity<List<NotificationResDTO>> showAllNotification() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User recipient = (User) authentication.getPrincipal();
        return ResponseEntity.ok(notificationService.showAllNotification(recipient));
    }
    @PostMapping("/view/{id}")
    public ResponseEntity<?> viewNotification(@PathVariable Long id) {
        notificationService.viewNotification(id);
        return ResponseEntity.ok().build();
    }
}
