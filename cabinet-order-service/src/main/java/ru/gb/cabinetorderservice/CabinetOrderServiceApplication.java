package ru.gb.cabinetorderservice;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(name = "openapibearer", scheme = "bearer", type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER)
public class CabinetOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CabinetOrderServiceApplication.class, args);
    }

}
