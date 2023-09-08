package ru.gb.api.dtos.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {
    String email;
    String subject;
    String firstName;
    String message;
}
