package org.miit.edf.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на регистрацию")
public class SignUpRequest {
    @Schema(description = "Логин пользователя", example = "Sapipa")
    @Size(min = 5, max = 50, message = "Логин пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Логин пользователя не может быть пустыми")
    private String username;
    @Schema(description = "Пароль", example = "my_1secret1_password")
    @Size(min = 8, max = 255, message = "Длина пароля должна быть от 8 до 255 символов")
    private String password;
    @Schema(description = "Имя пользователя", example = "Dima")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String firstName;
    @Schema(description = "Фамилия пользователя", example = "Garaj")
    @NotBlank(message = "Фамилия пользователя не может быть пустыми")
    private String lastName;
    @Schema(description = "Отчество пользователя", example = "Valerich")
    private String patronymic;
}
