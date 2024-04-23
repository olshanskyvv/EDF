package org.miit.edf.controllers;

import lombok.RequiredArgsConstructor;
import org.miit.edf.dto.response.NotificationResDTO;
import org.miit.edf.models.User;
import org.miit.edf.services.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    @GetMapping("/view")
    public ResponseEntity<List<NotificationResDTO>> viewAllNotification() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User recipient = (User) authentication.getPrincipal();
        return ResponseEntity.ok(notificationService.viewAllNotification(recipient));
    }
}
