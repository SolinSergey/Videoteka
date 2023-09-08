package ru.gb.cartservice;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.gb.api.dtos.cart.CartItemDto;
import ru.gb.cartservice.services.CartService;


import java.util.Optional;

//
// это значит что работаем в Spring Boot окружении
//(classes = CartService.class) - эта строчка означает что для работы этоготеста нужен только этот бин CartService - ускоряет работу
@SpringBootTest
public class CartTest {
//    // внедряем бинн CartService
//    @Autowired
//    private CartService cartService;
//
//
//    @BeforeEach
//    public void initCart() {
//        cartService.clearCart("test_cart1");
//    }
////
//
//    @Test
//    public void addToCartTest() {
//        CartItemDto cartItemDto = new CartItemDto();
//        cartItemDto.setFilmId(5L);
//        cartItemDto.setTitle("X");
//        cartItemDto.setImageUrlLink("test");
//        cartItemDto.setPrice(100);
//        cartItemDto.setSale(true);
//
//        cartService.addToCart("test_cart1","test_cart1", 5l, "X","test",100,true);
//        cartService.addToCart("test_cart1", "test_cart1",5l, "X","test",100, false );
//        cartService.addToCart("test_cart1","test_cart1", 5l, "X","test",100,true );
//
//
//
//        Assertions.assertEquals(1, cartService.getCurrentCart("test_cart1").getItems().size());
//    }
//    @Test
//    public void removeItemFromCart(){
//        CartItemDto cartItemDto = new CartItemDto();
//        cartItemDto.setFilmId(5L);
//        cartItemDto.setTitle("X");
//        cartItemDto.setImageUrlLink("test");
//        cartItemDto.setPrice(100);
//        cartItemDto.setSale(true);
//
//        CartItemDto cartItemDto1 = new CartItemDto();
//        cartItemDto.setFilmId(2l);
//        cartItemDto.setTitle("Y");
//        cartItemDto.setImageUrlLink("test1");
//        cartItemDto.setPrice(105);
//        cartItemDto.setSale(true);
//
//        cartService.addToCart("test_cart1", "test_cart1",5l, "X","test",100, true );
//        cartService.addToCart("test_cart1","test_cart1", 2l, "Y","test1",105, true );
//        cartService.removeItemFromCart("test_cart1",2l);
//        Assertions.assertEquals(1, cartService.getCurrentCart("test_cart1").getItems().size());
//    }
//
//    @Test
//    public void merge(){
//        // чистим  корзины если они есть елс их нет они создаются
//        cartService.clearCart("user_cart");
//        cartService.clearCart("guest_cart");
//        CartItemDto cartItemDto = new CartItemDto();
//        cartItemDto.setFilmId(5L);
//        cartItemDto.setTitle("X");
//        cartItemDto.setImageUrlLink("test");
//        cartItemDto.setPrice(100);
//        cartItemDto.setSale(true);
//
//        CartItemDto cartItemDto1 = new CartItemDto();
//        cartItemDto1.setFilmId(2L);
//        cartItemDto1.setTitle("Y");
//        cartItemDto1.setImageUrlLink("test");
//        cartItemDto1.setPrice(102);
//        cartItemDto1.setSale(true);
//
//
//        cartService.addToCart("user_cart","user_cart", 5l, "X","test",100,true );
//        cartService.addToCart("guest_cart", "guest_cart",2l, "Y","test",102, true );
//        cartService.merge("user_cart","guest_cart");
//        Assertions.assertEquals(2, cartService.getCurrentCart("user_cart").getItems().size());
//    }
}