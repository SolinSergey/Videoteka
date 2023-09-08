package ru.gb.cartservice.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.dtos.cart.CartDto;
import ru.gb.api.dtos.cart.CartItemDto;
import ru.gb.api.dtos.dto.StringResponse;
import ru.gb.cartservice.converters.CartConverter;
import ru.gb.cartservice.services.CartService;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Tag(name = "Корзина" , description = "Методы работы с корзиной ")
@SecurityRequirement(name = "openapibearer")
public class CartsController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @Operation(
            summary = "Получение корзины пользователя ",
            description = "Позволяет получить список фильмов находящихся в корзине пользователя "
    )
    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public CartDto getCart(@RequestHeader(required = false) String userId, @RequestParam  String uuid) {
        return cartConverter.modelToDto(cartService.getCurrentCart(getCurrentCartUuid(userId, uuid)));
    }
    @Operation(
            summary = "Токена пользователя ",
            description = "генерируем случайный набо символов и возвращаем клиенту"
    )
    @GetMapping("/generate")
    public StringResponse generate() {
        return new StringResponse(cartService.generateCartUuid());
    }

    @Operation(
            summary = "Добавление фильма в корзину ",
            description = "Добавление фильма в корзину"
    )
    @GetMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public StringResponse add(@RequestHeader(required = false) String userId,  @RequestParam  String uuid,
                              @RequestParam  Long filmId,  @RequestParam  String filmTitle,
                              @RequestParam String filmImageUrlLink,  @RequestParam  int  rentPrice,
                              @RequestParam  int  salePrice, @RequestParam boolean isSale,
                              @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {

        String token = authorization.substring(7);

        return cartService.addToCart(getCurrentCartUuid(userId, uuid), userId, filmId, filmTitle,
                filmImageUrlLink, salePrice, rentPrice, isSale, token);
    }

    @Operation(
            summary = "Удаляем фильм из корзины",
            description = "Удаляем фильм из корзины "
    )

    @DeleteMapping()
    @PreAuthorize("hasRole('USER')")
    public void remove(@RequestHeader(required = false) String userId, @RequestParam  String uuid, @RequestParam  Long filmId) {
        cartService.removeItemFromCart(getCurrentCartUuid(userId, uuid), filmId);
    }

    @Operation(
            summary = "Очищает вся корзину ",
            description = "Корзина чистится после оформления заказа "
    )
    @GetMapping("/clear")
    @PreAuthorize("hasRole('USER')")
    public void clear(@RequestHeader(required = false) String userId, @RequestParam  String uuid) {
        cartService.clearCart(getCurrentCartUuid(userId, uuid));
    }

    @Operation(
            summary = "Обеденение корзин",
            description = "Обеденение корзин не зарегестрированниго пользователея после регистрации"
    )
    @GetMapping("/merge")
    @PreAuthorize("hasRole('USER')")
    public void merge(@RequestHeader(required = false) String userId, @RequestParam  String uuid) {
        cartService.merge(
                getCurrentCartUuid(userId, null),
                getCurrentCartUuid(null, uuid)
        );
    }

    @Operation(
            summary = "Проверка корзины перед оплатой с бд фильм",
            description = "Проходимся по фильмам если в бд фильм уже удален то удаляем в корзине и обновляем корзину отпровляем сообщение об этом "
    )
    @GetMapping("/pay")
    public String pay(@RequestHeader(required = false) String userId) {
        return cartService.validateCart(userId);
    }

    @GetMapping ("/redis_content")
    public StringResponse redisContent (){
        return cartService.redisContent();
    }

    private String getCurrentCartUuid(String userId, String uuid) {
        if (userId != null) {
            return cartService.getCartUuidFromSuffix(userId);
        }
        return cartService.getCartUuidFromSuffix(uuid);
    }
}