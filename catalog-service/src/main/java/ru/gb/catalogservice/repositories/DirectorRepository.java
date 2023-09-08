package ru.gb.catalogservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.catalogservice.entities.Director;

import java.util.List;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
    List<Director> findAllByFirstNameInAndLastNameIn(String[] filterFirstName, String[] filterLastName);
    Director findByFirstNameAndLastName(String firstName, String lastName);
}