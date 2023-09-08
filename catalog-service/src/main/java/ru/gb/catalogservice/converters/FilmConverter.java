package ru.gb.catalogservice.converters;

import org.springframework.stereotype.Component;
import ru.gb.api.dtos.dto.FilmDto;
import ru.gb.api.dtos.dto.FilmTitleDto;
import ru.gb.catalogservice.entities.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class FilmConverter {
    public FilmDto entityToDto(Film film) {
        List<Price> prices = film.getPrices();
        Price price=null;
        for (Price p:prices){
            if (!p.isDeleted()){
                price=p;
                break;
            }
        }
        FilmDto filmDto = FilmDto.builder()
                .id(film.getId())
                .title(film.getTitle())
                .premierYear(film.getPremierYear())
                .description(film.getDescription())
                .imageUrlLink(film.getImageUrlLink())
                .genre(getGenreList(film.getGenres()))
                .country(getCountriesList(film.getCountries()))
                .director(getDirectorList(film.getDirectors()))
                .rentPrice(price.getPriceRent())
                .salePrice(price.getPriceSale())
                .build();
        return filmDto;
    }

    public FilmTitleDto titleByFilm (Film film) {
        return FilmTitleDto.builder()
                .id(film.getId())
                .title(film.getTitle())
                .build();
    }

    private List<String> getCountriesList(List<Country> list) {
        List<String> resultList = new ArrayList<>();
        for (Country c : list) {
            resultList.add(c.getTitle());
        }
        return resultList;
    }

    private List<String> getGenreList(List<Genre> list) {
        List<String> resultList = new ArrayList<>();
        for (Genre c : list) {
            resultList.add(c.getTitle());
        }
        return resultList;
    }

    private List<String> getDirectorList(List<Director> list) {
        List<String> resultList = new ArrayList<>();
        for (Director c : list) {
            resultList.add(c.getFirstName() + " " + c.getLastName());
        }
        return resultList;
    }

    private int getRentPrice(List<Price> prices){
        int result=0;
        for (Price p:prices){
            if (!p.isDeleted()){
                result=p.getPriceRent();
            }
            break;
        }
        return result;
    }

    private int getSalePrice(List<Price> prices){
        int result=0;
        for (Price p:prices){
            if (!p.isDeleted()){
                result=p.getPriceSale();
            }
            break;
        }
        return result;
    }
}
