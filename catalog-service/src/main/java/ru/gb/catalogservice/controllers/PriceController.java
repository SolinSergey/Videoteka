package ru.gb.catalogservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.api.dtos.dto.PriceDto;
import ru.gb.catalogservice.converters.PriceConverter;
import ru.gb.catalogservice.services.PriceService;


@RestController
@RequestMapping("/api/v1/price")
@RequiredArgsConstructor
@Tag(name = "Цены", description = "Методы для работы со списком цен")
@SecurityRequirement(name = "openapibearer")
public class PriceController {
    private final PriceService priceService;
    private final PriceConverter priceConverter;
    @Operation(
            summary = "Вывод цен",
            description = "Подготовка данных для ценового фильтра"
    )
    @GetMapping("prices_filter")
    public PriceDto getMinMaxPrices(){
        return priceConverter.entityToDto(priceService.findAllByIsDeletedIsFalse());
    }

}
