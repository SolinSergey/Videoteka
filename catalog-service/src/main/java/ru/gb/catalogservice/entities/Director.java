package ru.gb.catalogservice.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.gb.common.generic.entities.GenericEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="directors")
@Data
@RequiredArgsConstructor
public class Director extends GenericEntity {
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
}
