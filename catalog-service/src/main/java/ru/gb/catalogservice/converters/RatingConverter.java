package ru.gb.catalogservice.converters;

import org.springframework.stereotype.Component;
import ru.gb.api.dtos.dto.RatingDto;
import ru.gb.catalogservice.entities.Rating;


@Component
public class RatingConverter {
    public RatingDto entityToDto(Rating rating) {
        RatingDto ratingDto = RatingDto.builder()
                .user_id(rating.getUserId())
                .film_id(rating.getFilm().getId())
                .grade(rating.getGrade())
                .review(rating.getReview())
                .createDateTime(rating.getCreatedWhen())
                .build();
        return ratingDto;
    }
}
