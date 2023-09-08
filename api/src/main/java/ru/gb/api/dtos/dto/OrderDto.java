package ru.gb.api.dtos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private Long userId;
    private Long filmId;
    private String filmTitle;
    private String description;
    private String imageUrlLink;
    private int salePrice;
    private int rentPrice;
    private boolean isSale;
    private LocalDateTime rentStart;
    private LocalDateTime rentEnd;
}
