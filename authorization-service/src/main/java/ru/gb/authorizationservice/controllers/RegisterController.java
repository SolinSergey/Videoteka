package ru.gb.authorizationservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.api.dtos.dto.AppError;
import ru.gb.api.dtos.dto.RegisterUserDto;
import ru.gb.api.dtos.dto.StringResponse;
import ru.gb.authorizationservice.entities.User;
import ru.gb.authorizationservice.services.UserService;
import ru.gb.common.constants.InfoMessage;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reg")
@Tag(name = "Регистрация", description = "Метод регистрации пользователя")
public class RegisterController implements InfoMessage {
    private final UserService userService;

    @Operation(
            summary = "Запрос на регистрацию пользователя",
            responses = {
                    @ApiResponse(
                            description = ORDER_CREATED_SUCCESSFULLY, responseCode = "200",
                            content = @Content(schema = @Schema(implementation = StringResponse.class))
                    ),
                    @ApiResponse(
                            description = INPUT_DATA_ERROR,
                            responseCode = "400",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @PostMapping("/register")
    public StringResponse registerNewUser(@RequestBody RegisterUserDto registerUserDto) {

        Optional<User> user = userService.findByUsername(registerUserDto.getUsername());
      
        return user.map(u -> userService.restoreUser(registerUserDto, u))
                // пользователь есть - восстанавливаем если удален
                .orElseGet(() -> userService.createNewUser(registerUserDto));
                // пользователя нет - регистрируем
    }
}
