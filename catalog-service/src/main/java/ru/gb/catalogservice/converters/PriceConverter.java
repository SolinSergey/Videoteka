package ru.gb.catalogservice.converters;

import org.springframework.stereotype.Component;
import ru.gb.api.dtos.dto.PriceDto;
import ru.gb.catalogservice.entities.Price;

import java.util.List;

@Component
public class PriceConverter {
    public PriceDto entityToDto(List<Price> price){
        PriceDto priceDto=new PriceDto();
        priceDto.setMaxPriceSale(0);
        priceDto.setMaxPriceRent(0);
        priceDto.setMinPriceRent(price.get(0).getPriceRent());
        priceDto.setMinPriceSale(price.get(0).getPriceSale());
        for (int i=0;i< price.size();i++){
            Price p=price.get(i);
            if (p.getPriceSale()>priceDto.getMaxPriceSale()){
                priceDto.setMaxPriceSale(p.getPriceSale());
            }
            if (p.getPriceSale()<priceDto.getMinPriceSale()){
                priceDto.setMinPriceSale(p.getPriceSale());
            }
            if (p.getPriceRent()>priceDto.getMaxPriceRent()){
                priceDto.setMaxPriceRent(p.getPriceRent());
            }
            if (p.getPriceRent()<priceDto.getMinPriceRent()){
                priceDto.setMinPriceRent(p.getPriceRent());
            }
        }

        return priceDto;
    }
}
