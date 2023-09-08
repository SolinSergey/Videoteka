package ru.gb.catalogservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.catalogservice.entities.*;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long>, JpaSpecificationExecutor<Film> {
    @Query("select distinct f from Film f " +
            "join f.countries c join f.directors d join f.genres g join f.prices p" +
            " where (c in :countries) and (d in :directors) and (g in :genres)" +
            " and (p in :prices) and (f.premierYear>=:startPremierYear AND f.premierYear<=:endPremierYear)")
    Page<Film> findWithFilter(PageRequest pageRequest, List<Country> countries, List<Director> directors, List<Genre> genres,
                              int startPremierYear, int endPremierYear,List<Price> prices);

    @Query("select distinct f from Film f " +
            "join f.countries c join f.directors d join f.genres g join f.prices p " +
            "where (c in :countries) and (d in :directors) and (g in :genres)" +
            " and (p in :prices) and (f.premierYear>=:startPremierYear AND f.premierYear<=:endPremierYear) and (strpos(lower(f.title),:findString)>0) ")
    Page<Film> findWithFilterWithFindString(PageRequest pageRequest, List<Country> countries, List<Director> directors, List<Genre> genres,
                              int startPremierYear, int endPremierYear,List<Price> prices,String findString);

    List<Film> findAllByIsDeletedIsFalse();

}
