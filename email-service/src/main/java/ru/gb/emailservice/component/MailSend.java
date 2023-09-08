package ru.gb.emailservice.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.gb.api.dtos.dto.EmailDto;
import ru.gb.common.constants.Constant;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


@Component
public class MailSend implements Constant
{

    private final ObjectMapper objectMapper;

    private final JavaMailSenderImpl javaMailSender;
    private static final String SECRET_PATH="secret/";

    public MailSend(JavaMailSenderImpl javaMailSender) {
        objectMapper = new ObjectMapper();
        this.javaMailSender = javaMailSender;
        String line = null;

        try {
            FileReader reader = new FileReader(SECRET_PATH + "password.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            line = bufferedReader.readLine();

            javaMailSender.setPassword(line);
        } catch (Exception e) {
            e.printStackTrace();


        }
    }

    @RabbitListener(queues = MAIL_QUEUE_NAME)
    public void sendMessage(@Payload byte[] message) {

        try {
            EmailDto  emailDto = this.objectMapper.readValue(message, EmailDto.class);

        String email = emailDto.getEmail();
        String subject = emailDto.getSubject();
        String firstName = emailDto.getFirstName();
        String text = emailDto.getMessage();
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
            simpleMessage.setTo(email);
            simpleMessage.setSubject(subject);
            simpleMessage.setText("Здравствуйте, " + firstName+"! \n" + text);
        javaMailSender.send(simpleMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
