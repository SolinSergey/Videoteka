package ru.gb.catalogservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.gb.catalogservice.entities.Genre;
import ru.gb.catalogservice.exceptions.ResourceNotFoundException;
import ru.gb.catalogservice.repositories.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
    private final Sort SORT_GENRE = Sort.by("title").ascending();
    public Genre findById(Long id){
        return genreRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Жанр с id="+id+" не найден"));
    }
    public List<Genre> findByFilter(String[] filterGenre){
        return genreRepository.findAllByTitleIn(filterGenre);
    }
    public List<Genre> findAll(){
        return genreRepository.findAll(SORT_GENRE);
    }
}
