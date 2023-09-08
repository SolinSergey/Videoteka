package ru.gb.api.dtos.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceDto {
    private int minPriceSale=0;
    private int maxPriceSale=0;
    private int minPriceRent=0;
    private int maxPriceRent=0;
}
