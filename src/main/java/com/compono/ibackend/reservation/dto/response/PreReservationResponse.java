package com.compono.ibackend.reservation.dto.response;

import com.compono.ibackend.reservation.domain.PreReservation;
import java.time.LocalDateTime;

public record PreReservationResponse(
        Long Id,
        String email,
        String name,
        String phoneNumber,
        String expectation,
        LocalDateTime createdAt) {
    public static PreReservationResponse of(
            Long id,
            String email,
            String name,
            String phoneNumber,
            String expectation,
            LocalDateTime createdAt) {
        return new PreReservationResponse(id, email, name, phoneNumber, expectation, createdAt);
    }

    public static PreReservationResponse from(PreReservation preReservation) {
        return PreReservationResponse.of(
                preReservation.getId(),
                preReservation.getEmail(),
                preReservation.getName(),
                preReservation.getPhoneNumber(),
                preReservation.getExpectation(),
                preReservation.getCreatedAt());
    }
}
