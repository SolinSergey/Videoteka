package ru.gb.catalogservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.api.dtos.dto.GenreDto;
import ru.gb.catalogservice.converters.GenreConverter;
import ru.gb.catalogservice.services.GenreService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genre")
@RequiredArgsConstructor
@Tag(name = "Жанры", description = "Методы для работы со списком жанров")
@SecurityRequirement(name = "openapibearer")
public class GenreController {
    private final GenreService genreService;
    private final GenreConverter genreConverter;

    @Operation(
            summary = "Вывод названия жанра по id",
            description = "Позволяет вывести название жанра по заданному id"
    )
    @GetMapping("id")
    public GenreDto findById(@RequestParam Long id){
        return genreConverter.entityToDto(genreService.findById(id));
    }
    @Operation(
            summary = "Вывод списка жанров",
            description = "Позволяет вывести полный список жанров, имеющихся в БД"
    )
    @GetMapping("all")
    public List<GenreDto> listAll(){
        return genreService.findAll().stream().map(genreConverter::entityToDto).toList();
    }
}
