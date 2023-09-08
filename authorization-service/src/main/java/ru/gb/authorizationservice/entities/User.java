package ru.gb.authorizationservice.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.gb.common.generic.entities.GenericEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="users")
@Data
@RequiredArgsConstructor
public class User extends GenericEntity {
    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private String phoneNumber;

    @Column(name="address")
    private String address;

    @ManyToOne
    private Role role;

    @Override
    public String toString() {
        return "User [id=" + super.getId() +
                ", username=" + username +
                ", password=" + password +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", email=" + email +
                ", phoneNumber=" + phoneNumber +
                ", address=" + address +
                ", Role title=" + role.getTitle() + "]";
    }

}
