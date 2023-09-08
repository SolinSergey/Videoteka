package ru.gb.catalogservice.converters;

import org.springframework.stereotype.Component;
import ru.gb.api.dtos.dto.DirectorDto;
import ru.gb.catalogservice.entities.Director;


@Component
public class DirectorConverter {
    public DirectorDto entityToDto(Director director){
        DirectorDto directorDto=DirectorDto.builder()
                .id(director.getId())
                .firstName(director.getFirstName())
                .lastName(director.getLastName())
                .build();
        return directorDto;
    }
}
