package ru.gb.cartservice.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gb.api.dtos.dto.FilmDto;

@Component
@RequiredArgsConstructor
public class FilmServiceIntegration {
    private final WebClient filmServiceWebClient;


    public FilmDto findById(Long id) {
        FilmDto filmDto = filmServiceWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/film/id")
                        .queryParam("id", id)
                        .build())
                .retrieve()
                .bodyToMono(FilmDto.class)
                .block();
        return filmDto;
    }
}
