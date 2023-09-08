package ru.gb.cabinetorderservice.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.dtos.dto.AppError;
import ru.gb.api.dtos.dto.StringResponse;
import ru.gb.api.dtos.dto.OrderDto;
import ru.gb.cabinetorderservice.converters.OrderConverter;
import ru.gb.cabinetorderservice.entities.Order;
import ru.gb.cabinetorderservice.services.OrderService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Заказы" , description = "Методы работы с заказами")
@SecurityRequirement(name = "openapibearer")
public class OrdersController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @Operation(
            summary = "Создание заказа",
            description = "Созадение заказа "
    )
    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createOrder(@RequestHeader String userId,
                                         @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {

        String token = authorization.substring(7);
        String result = orderService.createOrder(userId, token);
        if (result.equals("Заказ успено сохранен в БД")) {
            return ResponseEntity.ok(new StringResponse(" Заказ успешно сохранен в БД"));

        }
        else if (result.equals("Ошибка интеграции")){
            return new ResponseEntity<>(new AppError("INTEGRATION_ERROR", "Ошибка интеграции"), HttpStatus.SERVICE_UNAVAILABLE);
        }

        else {
            return new ResponseEntity<>(new AppError("FILM_NOT_FOUND", result), HttpStatus.NOT_FOUND);
        }

    }

    @Operation(
            summary = "Заказ пользователя ",
            description = "Заказ пользователя "
    )
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<OrderDto> getCurrentUserOrders(@RequestHeader String userId) {
        Long userIDLong = Long.valueOf(userId);
        return orderService.findAllByUserId(userIDLong).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }

    @Operation(
            summary = "Просмотр фильма  ",
            description = "Просмотр фильма   "
    )
    @GetMapping("/play_film")
    @PreAuthorize("hasRole('USER')")
    public String findByFilmId(@RequestHeader String userId, @RequestParam Long filmId) {
        Long userIDLong = Long.valueOf(userId);
        Optional<Order> order1 = orderService.findFilmByUserIdAndFilmId(userIDLong, filmId);
        if (order1.isEmpty()) {
            return " Нельзя смотреть, фильм не оплачен " + filmId;
        }
        return "Приятного просмотра ";

    }
    @Operation(
            summary = " Фильм пользоватея ",
            description = "Возвращает фильм пользователя  "
    )
    @GetMapping("/user_film")
    @PreAuthorize("hasRole('USER')")
    public OrderDto findByFilmIdAndUserId(@RequestHeader String userId, @RequestParam Long filmId) {
        Long userIDLong = Long.valueOf(userId);
        Optional<Order> optionalOrder = orderService.findFilmByUserIdAndFilmId(userIDLong, filmId);
        if (optionalOrder.isEmpty())
            return new OrderDto();
        else {
            Order order1 = orderService.findFilmByUserIdAndFilmId(userIDLong, filmId).get();
            return orderConverter.entityToDto(order1);
        }
    }

    @Operation(
            summary = "удаление заказа  ",
            description = "удаление заказа из бд   "
    )
    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> delete(@RequestHeader String userId, @RequestParam Long filmId) {
        Long userIDLong = Long.valueOf(userId);
        String result = orderService.delete(userIDLong, filmId);
        if (result.equals("Фильм перезаписан в статусе удален")) {
            return ResponseEntity.ok(new StringResponse(" Фильм перезаписан в статусе удален"));

        } else {
            return new ResponseEntity<>(new AppError("FILM_NOT_FOUND", "Фильм не найден в заказах пользователя"), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Фильмы в аренде  ",
            description = "вытаскиваем из бд заказов фильмы пользователя которые в аренде  "
    )
    @GetMapping("/rent")
    @PreAuthorize("hasRole('USER')")
    public List<OrderDto> filmIsRent(@RequestHeader String userId) {
        Long userIDLong = Long.valueOf(userId);
        return orderService.filmIsRent(userIDLong).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }

    @Operation(
            summary = "Фильмы купленные",
            description = "вытаскиваем из бд заказов фильмы пользователя которые куплены "
    )
    @GetMapping("/sale")
    @PreAuthorize("hasRole('USER')")
    public List<OrderDto> filmIsSale(@RequestHeader String userId) {
        Long userIDLong = Long.valueOf(userId);
        return orderService.filmIsSale(userIDLong).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }
}