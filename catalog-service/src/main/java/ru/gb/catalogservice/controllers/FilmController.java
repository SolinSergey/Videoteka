package ru.gb.catalogservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.dtos.dto.FilmDto;
import ru.gb.api.dtos.dto.FilmTitleDto;
import ru.gb.api.dtos.dto.MinMaxYearDto;

import ru.gb.api.dtos.dto.StringResponse;
import ru.gb.catalogservice.converters.FilmConverter;
import ru.gb.catalogservice.entities.*;
import ru.gb.catalogservice.exceptions.IllegalInputDataException;
import ru.gb.catalogservice.services.*;
import ru.gb.catalogservice.utils.ResultOperation;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/api/v1/film")
@RequiredArgsConstructor
@Tag(name = "Фильмы", description = "Методы для работы со списком фильмов")
@SecurityRequirement(name = "openapibearer")
public class FilmController {
    private final FilmService filmService;
    private final FilmConverter filmConverter;

    @Operation(
            summary = "Вывод данных фильма по id",
            description = "Позволяет вывести данные фильма по заданному id"
    )
    @GetMapping("id")
    public FilmDto findById(@RequestParam Long id){
        Film film=filmService.findById(id);
        return filmConverter.entityToDto(film);
    }

    @Operation(
            summary = "Вывод списка фильмов для главной страницы",
            description = "Позволяет вывести полный список стран, имеющихся в БД с применением условий фильтров. Используется для подготовки главной страницы"
    )
    @GetMapping("all_with_filter")
    public Page<FilmDto> listAll(@RequestParam @Parameter(description = "Номер страницы (start=0)", required = true) int currentPage,
                                 @RequestParam (name="filterCountryList",required = false) String[] filterCountryList,
                                 @RequestParam (name="filterDirectorList",required = false) String[] filterDirectorList,
                                 @RequestParam (name="filterGenreList",required = false) String[] filterGenreList,
                                 @RequestParam (name="startPremierYear",required = false)Integer startPremierYear,
                                 @RequestParam (name="endPremierYear",required = false)Integer endPremierYear,
                                 @RequestParam (name="isSale",required = false)Boolean isSale,
                                 @RequestParam (name="minPrice",required = false)Integer minPrice,
                                 @RequestParam (name="maxPrice",required = false)Integer maxPrice,
                                 @RequestParam (name="findString",required = false)String findString){

        return filmService.findAllWithFilter(currentPage,filterCountryList,filterDirectorList,filterGenreList,
                startPremierYear,endPremierYear,isSale,minPrice,maxPrice,findString).map(filmConverter::entityToDto);
    }
    @Operation(
            summary = "Добавление фильма в БД",
            description = "Позволяет добавлять фильмы в БД"
    )
    @PostMapping("/new_film")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> addNewFilm(@RequestBody FilmDto filmDto) {
        ResultOperation resultOperation=filmService.filmAddOrChangeInVideoteka(filmDto);
        if (resultOperation.isResult()){
            return ResponseEntity.ok(new StringResponse(resultOperation.getResultDescription()));
        }else {
            throw new IllegalInputDataException(resultOperation.getResultDescription());
        }
    }
    @Operation(
            summary = "Изменение фильма в БД",
            description = "Позволяет изменять фильмы в БД"
    )
    @PutMapping("/movie_change")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> changeFilm(@RequestBody FilmDto filmDto) {
        ResultOperation resultOperation=filmService.filmAddOrChangeInVideoteka(filmDto);
        if (resultOperation.isResult()){
            return ResponseEntity.ok().body(HttpStatus.OK+" "+resultOperation.getResultDescription());
        }else {
            throw new IllegalInputDataException(resultOperation.getResultDescription());
        }
    }

    @Operation(
            summary = "Вывод максимального и минимального годов для главной страницы",
            description = "Позволяет вывести максимальный и минимальный годы, имеющихся в БД фильмов. Используется для подготовки главной страницы"
    )
    @GetMapping("min_max_year")
    public MinMaxYearDto findMinAndMaxYear() {
        List<Integer> allYears = filmService.findAll().stream().map(Film::getPremierYear).toList();
        int minYear = Collections.min(allYears);
        int maxYear = Collections.max(allYears);
        return new MinMaxYearDto(minYear, maxYear);
    }

    @Operation(
            summary = "Вывод всех неудаленных фильмов",
            description = "Позволяет получить полный общий список ВСЕХ фильмов"
    )
    @GetMapping("all")
    public List<FilmDto> findAllNotDeletedFilms() {
        return filmService.findAllNotDeletedFilms().stream().map(filmConverter::entityToDto).toList();
    }

    @Operation(
            summary = "Вывод всех имен неудаленных фильмов",
            description = "Позволяет получить полный общий список имен всех фильмов"
    )
    @GetMapping("titles")
    @PreAuthorize("hasRole('MANAGER')")
    public List<FilmTitleDto> findAllTitlesOfNotDeletedFilms() {
        return filmService.findAllNotDeletedFilms().stream().map(filmConverter::titleByFilm).toList();
    }
}
