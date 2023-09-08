package ru.gb.cabinetorderservice.configs;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Bean
    public RabbitTemplate rabbitTemplate(){
        // После того, как сообщение отправляется на коммутатор, это называется методом обратного вызова?
        connectionFactory.setPublisherConfirms(true);
        // сообщение Отправить с переключателя в очередь, называется ли метод обратного вызова
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());

        // Независимо от того, отправляется ли сообщение переключатель
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack) {
                    System.out.println("Сообщение отправлено ");
                }else{
                    System.out.println("Сообщение не отправлено ");
                  //  log.info(«Ошибка сообщения: CorrelationData ({}), ACK ({}), причина ({})", correlationData, ack, cause);
                }
            }
        });
        // Если вы вызываете метод setreturncallback, то обязательные должны быть правдой, в противном случае Exchange не находит очередь, потеряет сообщение без запуска обратного вызова.
        rabbitTemplate.setMandatory(true);
        // Независимо от того, отправляется ли сообщение с переключателя в очередь, если отправка не удалась, он позвонит методу ReturnMessage.
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("Потеря сообщения ");
            }
        });
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }
}

