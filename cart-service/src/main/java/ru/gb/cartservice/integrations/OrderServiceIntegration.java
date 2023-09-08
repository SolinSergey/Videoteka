package ru.gb.cartservice.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gb.api.dtos.dto.OrderDto;
import ru.gb.common.constants.InfoMessage;


@Component
@RequiredArgsConstructor
public class OrderServiceIntegration implements InfoMessage {
    private final WebClient orderServiceWebClient;


    public OrderDto findByFilmIdAndUserId(String userId, Long filmId, String token) {
        OrderDto orderDto = orderServiceWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/orders/user_film")
                        .queryParam("filmId", filmId)
                        .build())
                .header( "userId", userId)
                .header(HttpHeaders.AUTHORIZATION, BEARER + token)
                .retrieve()
                .bodyToMono(OrderDto.class)
                .block();
        return orderDto;

    }
}
