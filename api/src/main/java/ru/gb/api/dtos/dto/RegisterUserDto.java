package ru.gb.api.dtos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Модель запроса на регистрацию пользователя")
public class RegisterUserDto {

    @Schema(description = "Имя пользователя", required = true, maxLength = 15, minLength = 3,
            example = "IvanPetrov1")
    private String username;

    @Schema(description = "Пароль", required = true, maxLength = 32, minLength = 6,
            example = "КириллицаИлиLatinitsa123")
    private String password;

    @Schema(description = "Повторный ввод пароля", required = true, maxLength = 32, minLength = 6,
            example = "КириллицаИлиLatinitsa123")
    private String confirmPassword;

    @Schema(description = "Email пользователя", required = true, example = "mail@gmail.com")
    private String email;

    @Schema(description = "Имя пользователя", required = false, example = "Иван")
    private String firstName;

    @Schema(description = "Фамилия", required = false, example = "Петров")
    private String lastName;

    @Schema(description = "Телефон", required = false, example = "+79631478523")
    private String phoneNumber;

    @Schema(description = "Адрес", required = false, example = "Благовещенск, ул.Свободы д.25")
    private String address;


}

