package ru.gb.cabinetorderservice.configs;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gb.api.dtos.dto.EmailDto;
import ru.gb.common.constants.Constant;

@Configuration
public class MailConfigForRabbitMQ implements Constant {

    @Autowired
    RabbitTemplate rabbitTemplate;
    // Создать очередь
    @Bean
    public Queue mailQueue(){
        return new Queue(MAIL_QUEUE_NAME);
    }

    // Создать переключатель
    @Bean
    public Exchange mailExchange(){
        // долговечно: будь то постоянный (после закрытия rabritmq, в следующий раз, когда выключатель существует)
        return new DirectExchange(MAIL_EXCHANGE_NAME, true, false);
    }

    // Свяжите переключатели и очереди
    @Bean
    public Binding mailBinding(Queue mailQueue, Exchange mailExchange){
        Binding binding = BindingBuilder
                .bind(mailQueue)
                .to(mailExchange)
                .with(MAIL_ROUTE_KEY)
                .noargs();

        rabbitTemplate.convertAndSend(new EmailDto("","","",""));

        return binding;
    }
}

