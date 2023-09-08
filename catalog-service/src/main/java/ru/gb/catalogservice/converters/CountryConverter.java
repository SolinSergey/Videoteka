package ru.gb.catalogservice.converters;

import org.springframework.stereotype.Component;
import ru.gb.api.dtos.dto.CountryDto;
import ru.gb.catalogservice.entities.Country;


@Component
public class CountryConverter {
    public CountryDto entityToDto(Country country){
        CountryDto countryDto=CountryDto.builder()
                .id(country.getId())
                .title(country.getTitle())
                .build();
        return countryDto;
    }
}
