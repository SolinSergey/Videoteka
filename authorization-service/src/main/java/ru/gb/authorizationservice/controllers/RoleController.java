package ru.gb.authorizationservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.dtos.dto.AppError;
import ru.gb.api.dtos.dto.RoleChangeDto;
import ru.gb.api.dtos.dto.StringResponse;
import ru.gb.authorizationservice.services.UserService;
import ru.gb.common.constants.InfoMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
@Tag(name = "Роли", description = "Методы работы с ролями пользователя")
public class RoleController implements InfoMessage {

    private final UserService userService;

    @Operation(
            summary = "Запрос на изменение роли пользователя",
            responses = {
                    @ApiResponse(
                            description = ROLE_CHANGED_SUCCESSFULLY, responseCode = "200",
                            content = @Content(schema = @Schema(implementation = StringResponse.class))
                    ),
                    @ApiResponse(
                            description = USER_NOT_FOUND, responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @PutMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public StringResponse update(@RequestBody RoleChangeDto roleChangeDto,
                                 @RequestHeader String userId) {
        //  RoleChangeDto - какого пользователя изменяем
        //  userId - кто послал запрос на изменение пользователя
        userService.setRoleToUser(roleChangeDto.getChangeUserId(), userId, roleChangeDto.getRole());
        return new StringResponse(ROLE_CHANGED_SUCCESSFULLY);
    }


}