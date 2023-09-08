package ru.gb.api.dtos.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmDto implements Serializable {
    private Long id;
    private String title;
    private Integer premierYear;
    private String description;
    private String imageUrlLink;
    private List<String> genre;
    private List<String> country;
    private List<String> director;
    private Integer rentPrice;
    private Integer salePrice;
}
