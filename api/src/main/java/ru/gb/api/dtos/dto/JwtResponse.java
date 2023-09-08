package ru.gb.api.dtos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Модель ответа на запрос авторизации")
public class JwtResponse {
    @Schema(description = "Токен", required = true,
            example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImV4cCI6MTY3MzgzODkxOSwiaWF0IjoxNjczODAyOTE5fQ.Y3gN-8PxmClJuxX71-RM_Xffwfg30UqA9NU_tKgceUI")
    private String token;

    @Schema(description = "Роль пользователя", required = true, example = "'ROLE_USER'")
    private String role;

}
