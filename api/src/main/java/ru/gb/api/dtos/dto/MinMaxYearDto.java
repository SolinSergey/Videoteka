package ru.gb.api.dtos.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MinMaxYearDto {
    private int minYear;
    private int maxYear;
}
