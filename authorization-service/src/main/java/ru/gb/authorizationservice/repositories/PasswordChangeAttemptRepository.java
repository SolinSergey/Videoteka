package ru.gb.authorizationservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.authorizationservice.entities.PasswordChangeAttempt;

@Repository
public interface PasswordChangeAttemptRepository extends JpaRepository<PasswordChangeAttempt, Long> {

}
