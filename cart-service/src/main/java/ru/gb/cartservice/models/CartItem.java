package ru.gb.cartservice.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.api.dtos.cart.CartItemDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Long filmId;
    private String filmTitle;
    private String filmImageUrlLink;
    private int rentPrice;
    private int salePrice;
    private boolean isSale;

//    public CartItem(CartItemDto cartItemDto) {
//    }


    public CartItem(CartItemDto cartItemDto) { // (FilmDto filmdto)
        this.filmId = cartItemDto.getFilmId();
        this.filmTitle = cartItemDto.getTitle();
        this.filmImageUrlLink = cartItemDto.getImageUrlLink();
        this.salePrice = cartItemDto.getSalePrice();
        this.rentPrice = cartItemDto.getRentPrice();
        this.isSale = cartItemDto.isSale();

    }


}