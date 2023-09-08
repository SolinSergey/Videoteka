package ru.gb.authorizationservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.gb.authorizationservice.entities.Role;
import ru.gb.authorizationservice.entities.User;
import ru.gb.authorizationservice.repositories.UserRepository;
import ru.gb.common.constants.InfoMessage;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService, InfoMessage {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = findNotDeletedByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(INVALID_USERNAME_OR_PASSWORD);
        }
        return new org.springframework.security.core.userdetails
                .User(user.getUsername(), user.getPassword(),
                Collections.singleton(mapRoleToAuthority(user.getRole())));
        // Singleton потому что роль может быть только 1
    }

    private SimpleGrantedAuthority mapRoleToAuthority(Role role) {
        return new SimpleGrantedAuthority(role.getTitle());
    }

    public User findNotDeletedByUsername(String username) {   // найти не удаленного пользователя с нужным именем
        User user = findByUsername(username).orElseThrow
                (() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        if (!user.isDeleted()) {
            return user;
        } else {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String getRole(String username) {
        User user = findNotDeletedByUsername(username);
        return user.getRole().getTitle();
    }
}
