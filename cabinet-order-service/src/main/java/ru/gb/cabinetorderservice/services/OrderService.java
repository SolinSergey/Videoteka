package ru.gb.cabinetorderservice.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.api.dtos.cart.CartDto;
import ru.gb.api.dtos.cart.CartItemDto;
import ru.gb.api.dtos.dto.EmailDto;
import ru.gb.api.dtos.dto.UserNameMailDto;
import ru.gb.cabinetorderservice.entities.Order;
import ru.gb.cabinetorderservice.integrations.AuthServiceIntegration;
import ru.gb.cabinetorderservice.integrations.CartServiceIntegration;
import ru.gb.cabinetorderservice.repositories.OrdersRepository;
import ru.gb.common.constants.Constant;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService implements Constant {
    private final OrdersRepository ordersRepository;
    private final CartServiceIntegration cartServiceIntegration;
    private final AuthServiceIntegration authServiceIntegration;
    private final ObjectMapper objectMapper;


    private final Integer RENT_HOURS = 24;
    @Autowired
    private RabbitTemplate rabbitTemplate;



    @Transactional
    public String createOrder(String userId, String token) {
        EmailDto emailDto = new EmailDto();

        try {
            CartDto currentCart = cartServiceIntegration.getCart(userId, token);
            Long userIDLong = Long.valueOf(userId);
            UserNameMailDto userNameMailDto = authServiceIntegration.findById(userIDLong);
            emailDto.setEmail(userNameMailDto.getEmail());
            emailDto.setFirstName(userNameMailDto.getFirstName());
            String message = "";
            emailDto.setSubject("Оформление заказа");
            if (currentCart.getItems().size() <= 0) {
                return "Заказ не сохранен - корзина пуста";
            }

            for (CartItemDto cartItemDto : currentCart.getItems()) {
                Optional<Order> optionalOrder = findFilmByUserIdAndFilmId(userIDLong, cartItemDto.getFilmId());
                if (!optionalOrder.isEmpty()) {
                    Order order = findFilmByUserIdAndFilmId(userIDLong, cartItemDto.getFilmId()).get();
                    order.setType("SALE");
                    order.setRentStart(null);
                    order.setRentEnd(null);
                    emailDto.setMessage("\nВаш заказ успешно оформлен. Вы купили фильм \"" + cartItemDto.getTitle() + "\"\n \nСпасибо за покупку. Приятного просмотра \n \nВаша команда \"Видеотека\"");
                } else {
                    Order newOrder = new Order();
                    newOrder.setUserId(userIDLong);
                    newOrder.setSalePrice(cartItemDto.getSalePrice());
                    newOrder.setRentPrice(cartItemDto.getRentPrice());
                    newOrder.setFilmId(cartItemDto.getFilmId());
                    if (!cartItemDto.isSale()) {
                        newOrder.setType("RENT");
                        // текущее время
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                        LocalDateTime dateStart = Instant.ofEpochMilli(System.currentTimeMillis())
                                .atZone(ZoneId.of(Constant.SERVER_TIME_ZONE)).toLocalDateTime();
                        newOrder.setRentStart(dateStart);
                        // к текущей дате прибавили 24 часа
                        newOrder.setRentEnd(dateStart.plusHours(RENT_HOURS));// завести константу,
                        String formattedDateTime = dateStart.plusHours(RENT_HOURS).format(formatter);
                        emailDto.setMessage("\nВаш заказ успешно оформлен. Вы оплатили прокат фильма \"" + cartItemDto.getTitle()
                                + "\" \nСрок аренды составляет 24 часа и закончится " + formattedDateTime + "\n \nСпасибо за покупку. Приятного просмотра \n \nВаша команда \"Видеотека\"");


                    } else {
                        newOrder.setType("SALE");
                        emailDto.setMessage("\nВаш заказ успешно оформлен. Вы купили фильм \" " + cartItemDto.getTitle() + "\"\n \nСпасибо за покупку. Приятного просмотра \n \nВаша команда \"Видеотека\"");

                    }

                    ordersRepository.save(newOrder);
                    rabbitSend(emailDto);

                }
            }

            cartServiceIntegration.clearUserCart(userId, token);


            return "Заказ успено сохранен в БД";
        }
        catch (Exception e){
            return  "Ошибка интеграции";
        }
    }


    public void rabbitSend(EmailDto emailDto) {
        this.rabbitTemplate.setExchange(MAIL_EXCHANGE_NAME);
        this.rabbitTemplate.setRoutingKey(MAIL_ROUTE_KEY);
        Message message = null;
        try {
            // Сообщение Этот класс предоставляется Rabbitmq, и преобразует почтовую информацию по почте в организм для хранения памяти сообщения, установите режим передачи сообщения.
            message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(emailDto))
                    .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                    .build();
            // Установите атрибут головного сигнала сообщения, формат контента JSON
            message.getMessageProperties()
                    .setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, MessageProperties.CONTENT_TYPE_JSON);
            // Отправить почтовую информацию для переключения
            this.rabbitTemplate.convertAndSend(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    // возвращаем заказы пользователя проверяем если вермя проката изтекло то не выводим
    public List<Order> findAllByUserId(Long userId) {
        List<Order> orders = ordersRepository.findAllByUserId(userId);// возвращает список заказов с полем isDelete- false
        Iterator<Order> orderIterator = orders.iterator();
        while (orderIterator.hasNext()){
            Order orderNext= orderIterator.next();
            if (orderNext.getType().equals("RENT")) {
                // если время проката истекло то ставим статус в поле isDelete - false
                // пересохраняем фильм
                softDeleteOfOrderInRent(orderNext);
            }
        }

        return ordersRepository.findAllByUserId(userId);
    }
    @Transactional
    public boolean softDeleteOfOrderInRent(Order order){
        LocalDateTime dateNow = Instant.ofEpochMilli(System.currentTimeMillis())
                .atZone(ZoneId.of(Constant.SERVER_TIME_ZONE)).toLocalDateTime();
        if (order.getRentEnd().isBefore(dateNow)) {
            order.setDeleted(true);
            order.setDeletedWhen(dateNow);
            // пересохраняем заказ пользователя
            ordersRepository.save(order);
            return true;
        }
        return false;
    }
    @Transactional
    public void softDeleteOfOrder(Order order){
        LocalDateTime dateNow = Instant.ofEpochMilli(System.currentTimeMillis())
                .atZone(ZoneId.of(Constant.SERVER_TIME_ZONE)).toLocalDateTime();
            order.setDeleted(true);
            order.setDeletedWhen(dateNow);
            // пересохраняем заказ пользователя
            ordersRepository.save(order);

    }

    public String delete(Long userId, Long filmId) {
        List<Order> orders = ordersRepository.findByUserIdAndFilmId(userId, filmId);
        if (orders.size()>0) {
            Iterator<Order> orderIterator = orders.iterator();
            while (orderIterator.hasNext()){
                Order orderNext= orderIterator.next();
                softDeleteOfOrder(orderNext);
                    // если время проката истекло то ставим статус в поле isDelete - false
                    // пересохраняем фильм

                }
            return "Фильм перезаписан в статусе удален";
        }
        else {
            return " Этого фильма нет в бд," + filmId;
        }
    }


    public Optional<Order> findFilmByUserIdAndFilmId(Long userId, Long filmId) {
        List<Order> orders = ordersRepository.findByUserIdAndFilmId(userId, filmId);
        if (orders.size()>0){
            return Optional.ofNullable(orders.get(0));
        }
        else return Optional.empty();
    }
    public List<Order> filmIsRent (Long userId){
        return ordersRepository.findAllByUserIfFilmIsRent(userId);
    }
    public List<Order> filmIsSale (Long userId){
        return ordersRepository.findAllByUserIfFilmIsSale(userId);
    }
}