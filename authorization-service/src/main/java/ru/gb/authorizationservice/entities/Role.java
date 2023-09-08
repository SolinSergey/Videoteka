package ru.gb.authorizationservice.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="roles")
@Data
@RequiredArgsConstructor
public class Role {
    @Id
    @Column(name="id",nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "default_generator")
    private Long id;

    @Column(name="title")
    private String title;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "role_id")
    private List<User> users;
}





