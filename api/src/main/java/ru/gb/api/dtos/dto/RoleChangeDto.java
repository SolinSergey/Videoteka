package ru.gb.api.dtos.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleChangeDto {
    private Long changeUserId;
    private String role;
}
