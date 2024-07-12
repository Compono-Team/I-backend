package com.compono.ibackend.reservation.dto.response;

import com.compono.ibackend.reservation.domain.BetaReservation;
import java.time.LocalDateTime;

public record BetaReservationResponse(
        Long id, String email, String name, String phoneNumber, LocalDateTime createdAt) {

    public static BetaReservationResponse of(
            Long id, String email, String name, String phoneNumber, LocalDateTime createdAt) {
        return new BetaReservationResponse(id, email, name, phoneNumber, createdAt);
    }

    public static BetaReservationResponse from(BetaReservation betaReservation) {
        return BetaReservationResponse.of(
                betaReservation.getId(),
                betaReservation.getEmail(),
                betaReservation.getName(),
                betaReservation.getPhoneNumber(),
                betaReservation.getCreatedAt());
    }
}
