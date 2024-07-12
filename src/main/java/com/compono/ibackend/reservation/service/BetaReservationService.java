package com.compono.ibackend.reservation.service;

import static com.compono.ibackend.common.enumType.ErrorCode.DUPLICATED_FAILED;

import com.compono.ibackend.common.exception.BadRequestException;
import com.compono.ibackend.reservation.dto.request.BetaReservationRequest;
import com.compono.ibackend.reservation.dto.response.BetaReservationResponse;
import com.compono.ibackend.reservation.repository.BetaReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BetaReservationService {

    private final BetaReservationRepository betaReservationRepository;

    // 저장하기
    public BetaReservationResponse addBetaReservation(
            BetaReservationRequest betaReservationRequest) {
        if (betaReservationRepository.findByEmail(betaReservationRequest.email()).isPresent()) {
            throw new BadRequestException(DUPLICATED_FAILED);
        }
        return BetaReservationResponse.from(
                (betaReservationRepository.save(betaReservationRequest.toEntity())));
    }
}
