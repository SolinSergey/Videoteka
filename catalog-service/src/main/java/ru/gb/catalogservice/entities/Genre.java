package ru.gb.catalogservice.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.gb.common.generic.entities.GenericEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="genres")
@Data
@RequiredArgsConstructor
public class Genre extends GenericEntity {
    @Column(name="title")
    private String title;
}
