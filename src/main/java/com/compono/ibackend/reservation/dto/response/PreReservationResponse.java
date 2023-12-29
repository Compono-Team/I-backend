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

    public PreReservationResponse(PreReservation preReservation) {
        this(
                preReservation.getId(),
                preReservation.getEmail(),
                preReservation.getName(),
                preReservation.getPhoneNumber(),
                preReservation.getExpectation(),
                preReservation.getCreatedAt());
    }
}
