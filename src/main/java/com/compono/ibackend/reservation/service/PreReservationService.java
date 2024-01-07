package com.compono.ibackend.reservation.service;

import static com.compono.ibackend.common.enumType.ErrorCode.DUPLICATED_FAILED;
import static com.compono.ibackend.common.enumType.ErrorCode.NOT_FOUND_PRE_RESERVATION_ID;

import com.compono.ibackend.common.dto.page.request.PageRequest;
import com.compono.ibackend.common.dto.page.request.PageResponse;
import com.compono.ibackend.common.exception.BadRequestException;
import com.compono.ibackend.reservation.domain.PreReservation;
import com.compono.ibackend.reservation.dto.request.PreReservationRequest;
import com.compono.ibackend.reservation.dto.response.PreReservationResponse;
import com.compono.ibackend.reservation.repository.PreReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PreReservationService {

    private final PreReservationRepository preReservationRepository;

    // 저장하기
    public PreReservationResponse addPreReservation(PreReservationRequest preReservationRequest) {
        if (preReservationRepository.findByEmail(preReservationRequest.email()).isPresent()) {
            throw new BadRequestException(DUPLICATED_FAILED);
        }
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

    // 사전예약 Paging 조회
    @Transactional(readOnly = true)
    public PageResponse<PreReservationResponse> findAll(PageRequest pageRequest) {
        Pageable pageable = pageRequest.getPageRequest();
        return PageResponse.convertToPageResponse(
                preReservationRepository.findAll(pageable).map(PreReservationResponse::from));
    }
}
