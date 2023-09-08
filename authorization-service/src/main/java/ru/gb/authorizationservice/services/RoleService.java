package ru.gb.authorizationservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.authorizationservice.entities.Role;
import ru.gb.authorizationservice.exceptions.ResourceNotFoundException;
import ru.gb.authorizationservice.repositories.RoleRepository;
import ru.gb.common.constants.InfoMessage;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService implements InfoMessage {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByTitle("ROLE_USER").orElseThrow(
                () -> new ResourceNotFoundException(ROLE_NOT_FOUND));
    }

    public Optional<Role> getRoleByName(String role) {
        return roleRepository.findByTitle(role);
    }


}