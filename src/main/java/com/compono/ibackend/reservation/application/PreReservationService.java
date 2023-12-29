package com.compono.ibackend.reservation.application;

import static com.compono.ibackend.common.enumType.ErrorCode.NOT_FOUND_PRE_RESERVATION_ID;

import com.compono.ibackend.common.exception.BadRequestException;
import com.compono.ibackend.reservation.domain.PreReservation;
import com.compono.ibackend.reservation.domain.repository.PreReservationRepository;
import com.compono.ibackend.reservation.dto.request.PreReservationRequest;
import com.compono.ibackend.reservation.dto.response.PreReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PreReservationService {

    private final PreReservationRepository preReservationRepository;
    // 저장하기
    public PreReservationResponse addPreReservation(PreReservationRequest preReservationRequest) {
        return PreReservationResponse.from(
                (preReservationRepository.save(preReservationRequest.toEntity())));
    }
    // 사전예약 단건조회하기 (test)
    @Transactional(readOnly = true)
    public PreReservationResponse findPreReservation(Long id) {
        PreReservation preReservation =
                preReservationRepository
                        .findById(id)
                        .orElseThrow(() -> new BadRequestException(NOT_FOUND_PRE_RESERVATION_ID));
        return PreReservationResponse.from(preReservation);
    }
}
