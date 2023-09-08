package ru.gb.emailservice.services;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import ru.gb.api.dtos.dto.EmailDto;
import java.io.BufferedReader;
import java.io.FileReader;


@Service
public class MailService
{
    private final JavaMailSenderImpl javaMailSender;
    private static final String SECRET_PATH="secret/";

    public MailService(JavaMailSenderImpl javaMailSender) {
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


    public void sendMessage(EmailDto emailDto) {
        String email = emailDto.getEmail();
        String subject = emailDto.getSubject();
        String firstName = emailDto.getFirstName();
        String text = emailDto.getMessage();
        SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject(subject);
            message.setText("Здравствуйте, " + firstName+"! \n" + text);
        javaMailSender.send(message);

    }

    public void testMessage(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        // message.setFrom("Videoteka");
        message.setSubject("Оформление заказа");
        message.setText("Здравствуйте, Анна! \nВаш заказ успешно оформлен ");
        javaMailSender.send(message);

    }
    public String generateVerificationCode (String firstName, String email){
        EmailDto emailDto = new EmailDto();
        emailDto.setEmail(email);
        emailDto.setFirstName(firstName);
        emailDto.setSubject("Код верификации");
        int random = (int) (100000+(Math.random()*600000));
        String code = String.valueOf(random);
        SimpleMailMessage message = new SimpleMailMessage();
        emailDto.setMessage("Ваш код верификации - " + code);
        sendMessage(emailDto);
        return code;
    }


}
