package org.miit.edf.controllers;

import lombok.RequiredArgsConstructor;
import org.miit.edf.dto.response.UserDTO;
import org.miit.edf.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> userDTOS = userService.getAllUsers();
        if (userDTOS.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDTOS);
    }

    @GetMapping("/find")
    public ResponseEntity<List<UserDTO>> findByName(@RequestParam(required = false) String name) {
        if (name == null || name.isEmpty()) {
            return ResponseEntity.ok(userService.getAllUsers());
        }
        List<UserDTO> userDTOS = userService.findByName(name);
        if (userDTOS.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDTOS);
    }
}
