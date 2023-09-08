package ru.gb.authorizationservice.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="attempts")
@Data
public class PasswordChangeAttempt {
    @Id
    private Long id;

    @Column(name="created_when")
    private LocalDateTime createdWhen;

    @Column(name = "is_verified")
    private boolean isVerified;

    @Column(name = "verification_code")
    private String code;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;
}
