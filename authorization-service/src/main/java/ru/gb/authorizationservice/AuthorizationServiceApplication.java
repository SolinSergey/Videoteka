package ru.gb.authorizationservice;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(name = "openapibearer", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class AuthorizationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServiceApplication.class, args);


//        JwtTokenUtil jwtTokenUtil = null;         однократный запуск - генерация пары ключей
//        try {
//            jwtTokenUtil = new JwtTokenUtil();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            jwtTokenUtil.generateKeyPair();
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }


    }

}
