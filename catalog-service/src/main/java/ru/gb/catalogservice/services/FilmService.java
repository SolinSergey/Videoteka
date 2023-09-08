package ru.gb.catalogservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.gb.api.dtos.dto.FilmDto;
import ru.gb.api.dtos.dto.FilmTitleDto;
import ru.gb.catalogservice.entities.*;
import ru.gb.catalogservice.exceptions.IncorrectFilterParametrException;
import ru.gb.catalogservice.exceptions.NoDataException;
import ru.gb.catalogservice.exceptions.ResourceNotFoundException;
import ru.gb.catalogservice.repositories.FilmRepository;
import ru.gb.catalogservice.utils.DirectorsFilter;
import ru.gb.catalogservice.utils.ResultOperation;
import ru.gb.common.constants.InfoMessage;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService implements InfoMessage {
    private final int FILM_PAGE_SIZE = 10;
    private final FilmRepository filmRepository;
    private final CountryService countryService;
    private final DirectorService directorService;
    private final GenreService genreService;
    private final PriceService priceService;
    Sort sort = Sort.by("title").ascending();

    public Film findById(Long id) {
        return filmRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Фильм с id=" + id + " не найден"));
    }

    private ResultOperation checkFilmDto(FilmDto filmDto) {
        ResultOperation resultOperation = new ResultOperation();
        resultOperation.setResultDescription("");
        String temp = "";
        if (filmDto.getCountry() == null || filmDto.getCountry().size() == 0) {
            temp = resultOperation.getResultDescription() + "Не задана страна" + "-=-";
            resultOperation.setResultDescription(temp);
        }
        if (filmDto.getDirector() == null || filmDto.getDirector().size() == 0) {
            temp = resultOperation.getResultDescription() + "Не задан режиссер" + "-=-";
            resultOperation.setResultDescription(temp);
        }
        if (filmDto.getGenre() == null || filmDto.getGenre().size() == 0) {
            temp = resultOperation.getResultDescription() + "Не задан жанр" + "-=-";
            resultOperation.setResultDescription(temp);
        }
        if (filmDto.getDescription() == null || filmDto.getDescription().length() == 0) {
            temp = resultOperation.getResultDescription() + "Не задано описание фильма" + "-=-";
            resultOperation.setResultDescription(temp);
        }
        if (filmDto.getPremierYear() == null || filmDto.getPremierYear() < 1895) {
            temp = resultOperation.getResultDescription() + "Не задан год премьеры" + "-=-";
            resultOperation.setResultDescription(temp);
        }
        if (filmDto.getTitle() == null || filmDto.getTitle().length() == 0) {
            temp = resultOperation.getResultDescription() + "Не задано название фильма" + "-=-";
            resultOperation.setResultDescription(temp);
        }
        if (filmDto.getRentPrice() == null || filmDto.getRentPrice() < 0) {
            temp = resultOperation.getResultDescription() + "Не задана цена аренды" + "-=-";
            resultOperation.setResultDescription(temp);
        }
        if (filmDto.getSalePrice() == null || filmDto.getSalePrice() < 0) {
            temp = resultOperation.getResultDescription() + "Не задана цена продажи";
            resultOperation.setResultDescription(temp);
        }
        return resultOperation;
    }

    private Film prepareFilm(FilmDto filmDto) {
        Film film;
        if (filmDto.getId()!=null && filmDto.getId()>0){
            film=filmRepository.findById(filmDto.getId()).orElseThrow();
        }else{
            film=new Film();
        }
        film.setTitle(filmDto.getTitle());
        film.setPremierYear(filmDto.getPremierYear());
        if (filmDto.getImageUrlLink() != null) {
            film.setImageUrlLink(filmDto.getImageUrlLink());
        }
        String[] tempArray = filmDto.getCountry().toArray(new String[0]);
        List<Country> countries = countryService.findByFilter(tempArray);
        film.setCountries(countries);
        DirectorsFilter directorsFilter = new DirectorsFilter(filmDto.getDirector());
        List<Director> directors = directorService.findByFilter(directorsFilter.getFilterDirectorFirstName(), directorsFilter.getFilterDirectorLastName());
        film.setDirectors(directors);
        tempArray = filmDto.getGenre().toArray(new String[0]);
        List<Genre> genres = genreService.findByFilter(tempArray);
        film.setGenres(genres);
        film.setDescription(filmDto.getDescription());
        return film;
    }

    public ResultOperation filmAddOrChangeInVideoteka(FilmDto filmDto) {
        ResultOperation resultOperation;
        resultOperation = checkFilmDto(filmDto);
        if (resultOperation.getResultDescription().length() == 0) {
            Film film = prepareFilm(filmDto);
            film = filmRepository.saveAndFlush(film);
            if (filmDto.getRentPrice()!=null && filmDto.getSalePrice()!=null){
                if (filmDto.getId()>0){
                    Price price=priceService.findByFilmAndIsNotDeleted(film);
                    price.setDeleted(true);
                    priceService.save(price);
                }
                addPriceForFilm(film, filmDto);
            }
            if (filmDto.getId()==null || filmDto.getId()==0){
                resultOperation.setResultDescription("Фильм добавлен в БД");
            }else{
                resultOperation.setResultDescription("Фильм успешно изменен");
            }
            resultOperation.setResult(true);
        } else {
            resultOperation.setResult(false);
        }
        return resultOperation;
    }

    private void addPriceForFilm(Film film, FilmDto filmDto) {
        Price price = new Price();
        price.setPriceSale(filmDto.getSalePrice());
        price.setPriceRent(filmDto.getRentPrice());
        price.setFilm(film);
        priceService.save(price);
    }

    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    private List<Country> getCountries(String[] filterCountryList) {
        List<Country> countries;
        if (filterCountryList == null || filterCountryList.length == 0) {
            countries = countryService.findAll();
        } else {
            countries = countryService.findByFilter(filterCountryList);
        }
        return countries;
    }

    private List<Director> getDirectors(String[] filterDirectorList) {
        List<Director> directors;
        if (filterDirectorList == null || filterDirectorList.length == 0) {
            directors = directorService.findAll();
        } else {
            DirectorsFilter directorsFilter = new DirectorsFilter(filterDirectorList);
            directors = directorService.findByFilter(directorsFilter.getFilterDirectorFirstName(), directorsFilter.getFilterDirectorLastName());
        }
        return directors;
    }

    private List<Genre> getGenres(String[] filterGenreList) {
        List<Genre> genres;
        if (filterGenreList == null || filterGenreList.length == 0) {
            genres = genreService.findAll();
        } else {
            genres = genreService.findByFilter(filterGenreList);
        }
        return genres;
    }

    private List<Price> getPrices(Integer minPrice, Integer maxPrice, Boolean isSale) {
        List<Price> prices;
        if (isSale) {
            prices = priceService.findByFilterSalePrice(minPrice, maxPrice);
        } else {
            prices = priceService.findByFilterRentPrice(minPrice, maxPrice);
        }
        return prices;
    }

    public Page<Film> findAllWithFilter(int currentPage, String[] filterCountryList, String[] filterDirectorList,
                                        String[] filterGenreList, Integer startPremierYear, Integer endPremierYear,
                                        Boolean isSale, Integer minPrice, Integer maxPrice, String findString) {

        List<Country> countries = getCountries(filterCountryList);
        List<Director> directors = getDirectors(filterDirectorList);
        List<Genre> genres = getGenres(filterGenreList);
        if (startPremierYear == null || startPremierYear < 1900) {
            startPremierYear = 1900;
        }
        if (endPremierYear == null || endPremierYear > LocalDate.now().getYear()) {
            endPremierYear = LocalDate.now().getYear();
        }
        List<Price> prices;
        if (isSale != null && minPrice != null && maxPrice != null) {
            prices = getPrices(minPrice, maxPrice, isSale);
            if (findString == null || findString.length() == 0) {
                Page <Film> films_result=filmRepository.findWithFilter(PageRequest.of(currentPage, FILM_PAGE_SIZE, sort), countries,
                        directors, genres, startPremierYear, endPremierYear, prices);
                System.out.println(films_result.getTotalElements());
                if (films_result.getTotalElements()==0){
                    throw new NoDataException(NO_DATA);
                }else {
                    return films_result;
                }
            } else {
                findString = findString.toLowerCase();
                Page<Film> films_result=filmRepository.findWithFilterWithFindString(PageRequest.of(currentPage, FILM_PAGE_SIZE, sort), countries,
                        directors, genres, startPremierYear, endPremierYear, prices, findString);
                System.out.println(films_result.getTotalElements());
                if (films_result.getTotalElements()==0){
                    throw new NoDataException(NO_DATA);
                }
                return films_result;
            }

        } else {
            throw new IncorrectFilterParametrException(INCORRECT_FILTER_PARAMETR);
        }
    }

    public List<Film> findAllNotDeletedFilms() {
        return filmRepository.findAllByIsDeletedIsFalse();
    }

}