package ru.gb.catalogservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.catalogservice.entities.Film;
import ru.gb.catalogservice.entities.Price;
import ru.gb.catalogservice.repositories.PriceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceService {
    private final PriceRepository priceRepository;
    public List<Price> findAll(){
        return priceRepository.findAll();
    }
    public List<Price> findAllByIsDeletedIsFalse(){
        return priceRepository.findAllByIsDeletedIsFalse();
    }
    public List<Price> findByFilterSalePrice(int minSalePrice,int maxSalePrice){
        return priceRepository.findAllByIsDeletedIsFalseAndPriceSaleBetween(minSalePrice,maxSalePrice);
    }
    public List<Price> findByFilterRentPrice(int minRentPrice,int maxRentPrice){
        return priceRepository.findAllByIsDeletedIsFalseAndPriceRentBetween(minRentPrice,maxRentPrice);
    }
    public void save(Price price){
        priceRepository.save(price);
    }

    public Price findByFilmAndIsNotDeleted(Film film){
        return priceRepository.findByFilmAndIsDeletedIsFalse(film);
    }
}
