package ru.gb.api.dtos.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectorDto {
    private Long id;
    private String firstName;
    private String lastName;
}
