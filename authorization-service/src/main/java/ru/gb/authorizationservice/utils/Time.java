package ru.gb.authorizationservice.utils;

import org.springframework.stereotype.Component;
import ru.gb.common.constants.Constant;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class Time {

    public LocalDateTime now() {
        return Instant.ofEpochMilli(System.currentTimeMillis())
                .atZone(ZoneId.of(Constant.SERVER_TIME_ZONE)).toLocalDateTime();
    }
}
