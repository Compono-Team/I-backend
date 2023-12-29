package com.compono.ibackend.reservation.presentation;

import com.compono.ibackend.reservation.application.PreReservationService;
import com.compono.ibackend.reservation.dto.request.PreReservationRequest;
import com.compono.ibackend.reservation.dto.response.PreReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pre-reservation")
public class PreReservationController {

    private final PreReservationService preReservationService;

    @PostMapping
    public ResponseEntity<PreReservationResponse> addPreReservation(
            PreReservationRequest preReservationRequest) {
        return ResponseEntity.ok()
                .body(preReservationService.addPreReservation(preReservationRequest));
    }

    @GetMapping("/{preReservationId}")
    public ResponseEntity<PreReservationResponse> getPreReservation(
            @PathVariable(name = "preReservationId") Long preReservationId) {
        return ResponseEntity.ok().body(preReservationService.findPreReservation(preReservationId));
    }
}