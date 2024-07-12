package com.compono.ibackend.reservation.controller;

import com.compono.ibackend.reservation.dto.request.BetaReservationRequest;
import com.compono.ibackend.reservation.dto.response.BetaReservationResponse;
import com.compono.ibackend.reservation.service.BetaReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/beta-reservation")
public class BetaReservationController {

    private final BetaReservationService betaReservationService;

    @PostMapping
    public ResponseEntity<BetaReservationResponse> addBetaReservation(
            @RequestBody BetaReservationRequest betaReservationRequest) {
        return ResponseEntity.ok()
                .body(betaReservationService.addBetaReservation(betaReservationRequest));
    }
}
