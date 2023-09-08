package ru.gb.api.dtos.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long filmId;
    private String title;
    private String imageUrlLink;
    private int salePrice;
    private int rentPrice;
    private boolean isSale;

}



