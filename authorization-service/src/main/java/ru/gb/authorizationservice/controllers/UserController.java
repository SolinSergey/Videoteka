package ru.gb.authorizationservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.dtos.dto.*;
import ru.gb.authorizationservice.converters.UserConverter;
import ru.gb.authorizationservice.services.UserService;
import ru.gb.common.constants.InfoMessage;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "Пользователи", description = "Методы работы с базой пользователей")
@SecurityRequirement(name = "openapibearer")
public class UserController implements InfoMessage {

    private final UserService userService;

    private final UserConverter userConverter;


    @Operation(
            summary = "Запрос на удаление пользователя",
            responses = {
                    @ApiResponse(
                            description = USER_DELETED_SUCCESSFULLY, responseCode = "200",
                            content = @Content(schema = @Schema(implementation = StringResponse.class))
                    ),
                    @ApiResponse(
                            description = USER_NOT_FOUND, responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    ),
                    @ApiResponse(
                            description = ADMIN_NOT_FOUND, responseCode = "400",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @DeleteMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public StringResponse deleteUserById(@RequestParam Long deleteUserId, @RequestHeader String userId) {
        //  deleteUserId - какого пользователя удаляем
        //  userId - кто послал запрос на удаление пользователя
        userService.safeDeleteById(deleteUserId, userId);
        return new StringResponse(USER_DELETED_SUCCESSFULLY);
    }

    @Operation(
            summary = "Вывод всех не удаленных пользователей на странице админа"
    )
    @GetMapping("not_deleted")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDto> listAllNotDeleted() {
        return userService.findAllNotDeleted().stream().map(userConverter::entityToDto).toList();
    }

    @Operation(
            summary = "Вывод всех пользователей на странице админа, включая удаленных"
    )
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDto> listAll() {
        return userService.findAll().stream().map(userConverter::entityToDto).toList();
    }

    @Operation(
            summary = "Имя и мэйл по id",
            description = "Вывод имени и емэйла пользователя по id",
            responses = {
                    @ApiResponse(
                            description = USER_FOUND, responseCode = "200"
                    ),
                    @ApiResponse(
                            description = USER_NOT_FOUND, responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("name_and_email_by_id")
    public UserNameMailDto getNameAndEmailById(@RequestParam Long id) {
        return userConverter.entityToNameMailDto(userService.findNameEmailById(id));
    }

    @Operation(
            summary = "Фамилия и имя по id",
            responses = {
                    @ApiResponse(
                            description = FULLNAME, responseCode = "200",
                            content = @Content(schema = @Schema(implementation = StringResponse.class))
                    ),
                    @ApiResponse(
                            description = USER_NOT_FOUND, responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("fullname_by_id")
    public StringResponse fullNameById(@RequestParam Long userId) {
        return userService.fullNameById(userId);
    }



    @Operation(
            summary = "Рейтинги с именами",
            responses = {
                    @ApiResponse(
                            description = "Рейтинги вместе с именами пользователей", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = StringResponse.class))
                    )
            }
    )
    @PostMapping("adding_names_to_ratings")
    public List<ReviewWithNameDto> addingNamesToRatings(@RequestBody List<RatingDto> ratings) {
        return userService.addingNamesToRatings(ratings);
    }


    @Operation(
            summary = "Создание попытки смены пароля",
            responses = {
                    @ApiResponse(
                            description = CONFIRMATION_CODE_SENT, responseCode = "200",
                            content = @Content(schema = @Schema(implementation = StringResponse.class))
                    ),
                    @ApiResponse(
                            description = USER_NOT_FOUND, responseCode = "400",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    ),
                    @ApiResponse(
                            description = INCORRECT_EMAIL, responseCode = "403",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @PostMapping("/password_attempt")
    public StringResponse setPasswordChangeAttempt(@RequestHeader String userId,
                                                   @RequestParam String email) {
        userService.setPasswordChangeAttempt(userId, email);
        return new StringResponse(CONFIRMATION_CODE_SENT);
    }

    @Operation(
            summary = "Проверка кода на смену пароля",
            responses = {
                    @ApiResponse(
                            description = CORRECT_CODE, responseCode = "200",
                            content = @Content(schema = @Schema(implementation = StringResponse.class))
                    ),
                    @ApiResponse(
                            description = USER_NOT_FOUND, responseCode = "400",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    ),
                    @ApiResponse(
                            description = INCORRECT_CODE, responseCode = "403",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @PutMapping("/code_check")
    public StringResponse checkCodeForPasswordChange(@RequestHeader String userId,
                                                   @RequestParam String code) {
        return userService.checkCodeForPasswordChange(userId, code);
    }

    @Operation(
            summary = "Сохранение пароля в базу",
            responses = {
                    @ApiResponse(
                            description = PASSWORD_UPDATED_SUCCESSFULLY, responseCode = "200",
                            content = @Content(schema = @Schema(implementation = StringResponse.class))
                    ),
                    @ApiResponse(
                            description = USER_NOT_FOUND, responseCode = "400",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    ),
                    @ApiResponse(
                            description = PASSWORD_VERIFICATION_ERROR, responseCode = "403",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @PutMapping("/password")
    public StringResponse updatePassword(@RequestHeader String userId, @RequestParam String password,
                                         @RequestParam String confirmPassword) {
        return userService.updatePassword(userId, password, confirmPassword);
    }

}

