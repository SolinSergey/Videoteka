package ru.gb.catalogservice.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.gb.common.generic.entities.GenericEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "grades_reviews")
@Data
@RequiredArgsConstructor
public class Rating extends GenericEntity {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "grade")
    private int grade;

    @Column(name = "review")
    private String review;

    @ManyToOne
    private Film film;

    @Column(name="is_moderate")
    private boolean isModerate;
}
