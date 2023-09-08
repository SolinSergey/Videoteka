package ru.gb.api.dtos.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingDto {
    private Long film_id;
    private Long user_id;
    private Integer grade;
    private String review;
    private LocalDateTime createDateTime;

    @Override
    public String toString() {
        return "RatingDto{" +
                "film_id=" + film_id +
                ", user_id=" + user_id +
                ", grade=" + grade +
                ", review='" + review + '\'' +
                ", localDateTime=" + createDateTime +
                '}';
    }
}
