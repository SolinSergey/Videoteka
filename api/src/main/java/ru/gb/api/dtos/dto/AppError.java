package ru.gb.api.dtos.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppError {
    private String code;
    private String value;
}
