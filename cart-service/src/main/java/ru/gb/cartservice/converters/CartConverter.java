package ru.gb.cartservice.converters;


import org.springframework.stereotype.Component;
import ru.gb.api.dtos.cart.CartDto;
import ru.gb.api.dtos.cart.CartItemDto;
import ru.gb.cartservice.models.Cart;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartConverter {
    public CartDto modelToDto(Cart cart) {
        List<CartItemDto> cartItemDtos = cart.getItems().stream().map(it ->
                new CartItemDto(
                        it.getFilmId(),
                        it.getFilmTitle(),
                        it.getFilmImageUrlLink(),
                        it.getSalePrice(),
                        it.getRentPrice(),
                        it.isSale())
        ).collect(Collectors.toList());
        CartDto cartDto = new CartDto(cartItemDtos, cart.getTotalPrice());
        return cartDto;
    }
}
