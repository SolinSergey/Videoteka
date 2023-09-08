package ru.gb.catalogservice.converters;

import org.springframework.stereotype.Component;
import ru.gb.api.dtos.dto.GenreDto;
import ru.gb.catalogservice.entities.Genre;

@Component
public class GenreConverter {
    public GenreDto entityToDto(Genre genre){
        GenreDto genreDto=GenreDto.builder()
                .id(genre.getId())
                .title(genre.getTitle())
                .build();
        return genreDto;
    }
}
