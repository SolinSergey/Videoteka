package ru.gb.catalogservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.dtos.dto.CountryDto;
import ru.gb.catalogservice.converters.CountryConverter;
import ru.gb.catalogservice.exceptions.IllegalInputDataException;
import ru.gb.catalogservice.services.CountryService;
import ru.gb.catalogservice.utils.ResultOperation;

import java.util.List;

@RestController
@RequestMapping("/api/v1/country")
@RequiredArgsConstructor
@Tag(name = "Страны", description = "Методы для работы со списком стран")
@SecurityRequirement(name = "openapibearer")
public class CountryController {
    private final CountryService countryService;
    private final CountryConverter countryConverter;

    @Operation(
            summary = "Вывод названия страны по id",
            description = "Позволяет вывести название страны по заданному id"
    )
  
    @GetMapping("id")
    public CountryDto findById(@RequestParam Long id){
        return countryConverter.entityToDto(countryService.findById(id));
    }

    @Operation(
            summary = "Вывод списка стран",
            description = "Позволяет вывести полный список стран, имеющихся в БД"
    )

    @GetMapping("all")
    public List<CountryDto> listAll(){
        return countryService.findAll().stream().map(countryConverter::entityToDto).toList();
    }

    @Operation(
            summary = "Добавление новой страны",
            description = "Позволяет добавить в БД новую страну"
    )
    @PostMapping("/new")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> addNewFilm(@RequestBody CountryDto countryDto) {
        ResultOperation resultOperation = countryService.countryAddInVideoteka(countryDto);
        if (resultOperation.isResult()) {
            return ResponseEntity.ok().body(HttpStatus.OK + " " + resultOperation.getResultDescription());
        } else {
            throw new IllegalInputDataException(resultOperation.getResultDescription());
        }
    }
}
