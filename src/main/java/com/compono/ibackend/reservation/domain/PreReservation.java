package com.compono.ibackend.reservation.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "`pre_reservation`")
public class PreReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "expectation", length = 1000)
    private String expectation;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private PreReservation(String email, String name, String phoneNumber, String expectation) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.expectation = expectation;
    }

    public static PreReservation of(
            String email, String name, String phoneNumber, String expectation) {
        return new PreReservation(email, name, phoneNumber, expectation);
    }

    public static PreReservation of(String email, String name, String phoneNumber) {
        return new PreReservation(email, name, phoneNumber, null);
    }
}
