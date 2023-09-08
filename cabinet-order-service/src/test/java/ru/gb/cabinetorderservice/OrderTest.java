package ru.gb.cabinetorderservice;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.gb.cabinetorderservice.entities.Order;
import ru.gb.cabinetorderservice.integrations.CartServiceIntegration;
import ru.gb.cabinetorderservice.repositories.OrdersRepository;
import ru.gb.cabinetorderservice.services.OrderService;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//
// это значит что работаем в Spring Boot окружении
//(classes = CartService.class) - эта строчка означает что для работы этоготеста нужен только этот бин CartService - ускоряет работу
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class OrderTest {
//    // внедряем бинн CartService
//    @Autowired
//    private OrderService orderService;
//
//
//
//
//
//    @Mock
//    private OrdersRepository ordersRepository;
//
//    @Mock
//    private CartServiceIntegration cartServiceIntegration;
//
//
//
////    @BeforeEach
////    public void initCart() {
////        cartService.clearCart("1");
////    }
////
//
//    @Test
//    public void findAll() {
////        List<CartItemDto> cartItems = new ArrayList<>();
////        CartItemDto cartItem = new CartItemDto();
////        cartItem.setFilmId(5L);
////        cartItem.setTitle("X");
////        cartItem.setImageUrlLink("test");
////        cartItem.setPrice(100);
////        cartItem.setSale(true);
////        cartItems.add(cartItem);
////        CartDto cart = new CartDto();
////        cart.setItems(cartItems);
////        cart.setTotalPrice(100);
////
////        Mockito.doReturn(cart).when(cartServiceIntegration.getCart("1"));
//
//
//
//        Order order = new Order();
//        order.setUserId(1L);
//        order.setId(1L);
//        order.setFilmId(5L);
//        order.setPrice(100);
//        order.setType("SALE");
//
//        when(ordersRepository.findAll()).thenReturn(List.of(order));
//       List<Order> orders = this.orderService.findAllByUserId(1L);
//
//
//
////        Mockito.doReturn(Optional.of(orders))
////                        .when(ordersRepository)
////                                .findAllByUserId(1L);
//
//
//      //  Assertions.assertNotNull(orders);
//
//        //orderService.createOrder(1l);
//        Assertions.assertEquals(List.of(order), orders);
//        verify(this.ordersRepository).findAllByUserId(1L);
//      //  Mockito.verify(ordersRepository,Mockito.times(1)).findAllByUserId(ArgumentMatcher.)
//    }
}
