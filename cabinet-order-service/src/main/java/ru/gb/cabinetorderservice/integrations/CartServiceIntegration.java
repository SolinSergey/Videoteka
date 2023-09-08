package ru.gb.cabinetorderservice.integrations;



import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.gb.api.dtos.cart.CartDto;
import ru.gb.common.constants.InfoMessage;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration implements InfoMessage {
    private final WebClient cartServiceWebClient;

    public void clearUserCart(String userId, String token) {
        cartServiceWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/cart/clear")
                        .queryParam("uuid", userId)
                        .build())
                .header("userId", userId)
                .header(HttpHeaders.AUTHORIZATION, BEARER + token)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public CartDto getCart(String userId, String token) {
        CartDto cart = cartServiceWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/cart")
                        .queryParam("uuid", userId)
                        .build())
                .header( "userId", userId)
                .header(HttpHeaders.AUTHORIZATION, BEARER + token)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError,
                        error -> Mono.error(new RuntimeException("Server is not responding")))
                .bodyToMono(CartDto.class)
                .block();
        return cart;
    }
}