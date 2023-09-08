package ru.gb.authorizationservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gb.common.constants.Constant;

@Configuration
public class MailConfigForRabbitMQ implements Constant {

    @Bean
    public Queue mailQueue(){
        return new Queue(MAIL_QUEUE_NAME);
    }

    @Bean
    public Exchange mailExchange(){
        return new DirectExchange(MAIL_EXCHANGE_NAME, true, false);
    }

    @Bean
    public Binding mailBinding(Queue mailQueue, Exchange mailExchange){
        return BindingBuilder
                .bind(mailQueue)
                .to(mailExchange)
                .with(MAIL_ROUTE_KEY)
                .noargs();
    }
}
