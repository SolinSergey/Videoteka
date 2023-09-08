package ru.gb.api.dtos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на авторизацию")
public class JwtRequest {

    @Schema(description = "Имя пользователя", required = true, maxLength = 36, minLength = 2, example = "alex")
    private String username;

    @Schema(description = "Пароль", required = true, maxLength = 80, minLength = 2, example = "some1password2")
    private String password;
}
