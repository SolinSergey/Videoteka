package ru.gb.catalogservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.catalogservice.entities.Country;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long>{
    List<Country> findAllByTitleIsIn(String[] strings);
    Country findByTitle(String title);
}
