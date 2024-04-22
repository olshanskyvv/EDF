package org.miit.edf.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.miit.edf.dto.JwtAuthenticationResponse;
import org.miit.edf.dto.PasswordDTO;
import org.miit.edf.dto.SignInRequest;
import org.miit.edf.dto.SignUpRequest;
import org.miit.edf.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth/users")
@RequiredArgsConstructor
@Tag(name = "Аутентификация пользователя")
@Log4j2
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody @Valid SignUpRequest request) {
        log.info("Регистрация пользователя: " + request.getUsername());
        return ResponseEntity.ok(authService.signUp(request));
    }

    @Operation(summary = "Вход пользователя")
    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid SignInRequest request) {
        log.info("Вход пользователя: " + request.getUsername());
        return ResponseEntity.ok(authService.signIn(request));
    }

    @ResponseBody
    @Operation(
            summary = "Обновление пароля",
            parameters = @Parameter(in = ParameterIn.HEADER,
                    name = "Authorization",
                    description = "JWT токен",
                    required = true,
                    example = "Bearer <token>")
    )
    @PostMapping("/password/update")
    public ResponseEntity<String> updatePassword(@RequestBody PasswordDTO passwordDTO) {
        log.info("Обновление пароля");
        authService.updatePassword(passwordDTO);
        return ResponseEntity.ok("Password was updated");
    }
}
