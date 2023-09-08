package ru.gb.api.dtos.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewWithNameDto {
    private Long film_id;
    private Long user_id;
    private String fullName;
    private Integer grade;
    private String review;
    private LocalDateTime createDateTime;

    @Override
    public String toString() {
        return "RatingDto{" +
                "film_id=" + film_id +
                ", user_id=" + user_id +
                ", fullName=" + fullName +
                ", grade=" + grade +
                ", review='" + review + '\'' +
                ", localDateTime=" + createDateTime +
                '}';
    }
}
