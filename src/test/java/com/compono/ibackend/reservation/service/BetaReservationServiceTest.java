package com.compono.ibackend.reservation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.compono.ibackend.common.exception.BadRequestException;
import com.compono.ibackend.reservation.domain.BetaReservation;
import com.compono.ibackend.reservation.dto.request.BetaReservationRequest;
import com.compono.ibackend.reservation.dto.response.BetaReservationResponse;
import com.compono.ibackend.reservation.repository.BetaReservationRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("[비즈니스 로직 - 베타테스터 신청]")
@ExtendWith(MockitoExtension.class)
class BetaReservationServiceTest {

    @InjectMocks private BetaReservationService betaReservationService;

    @Mock private BetaReservationRepository betaReservationRepository;

    @DisplayName("베타테스터 신청 정보를 정상적으로 생성한다.")
    @Test
    void givenBetaReservationRequest_whenAddBetaReservation_thenBetaRegisterResponse() {
        // given
        BetaReservationRequest betaReservationRequest = createBetaReservationRequest();
        BetaReservation betaReservation = createBetaReservation();
        given(betaReservationRepository.save(any(BetaReservation.class)))
                .willReturn(betaReservation);

        // when
        BetaReservationResponse betaReservationResponse =
                betaReservationService.addBetaReservation(betaReservationRequest);

        // then
        assertThat(betaReservationResponse)
                .isNotNull()
                .hasFieldOrPropertyWithValue("email", betaReservationRequest.email())
                .hasFieldOrPropertyWithValue("name", betaReservationRequest.name())
                .hasFieldOrPropertyWithValue("phoneNumber", betaReservationRequest.phoneNumber());

        then(betaReservationRepository).should().save(any(BetaReservation.class));
    }

    @DisplayName("베타테스터 신청 시, 중복된 이메일이 존재하면 예외를 던진다.")
    @Test
    void givenDuplicatedEmail_whenAddBetaReservation_thenThrowsBadRequestException()
            throws Exception {
        // given
        BetaReservationRequest betaReservationRequest = createBetaReservationRequest();
        BetaReservation existingReservation = createBetaReservation();

        given(betaReservationRepository.findByEmail(betaReservationRequest.email()))
                .willReturn(Optional.of(existingReservation));

        // when
        Throwable t =
                catchThrowable(
                        () -> betaReservationService.addBetaReservation(betaReservationRequest));

        // then
        assertThat(t).isInstanceOf(BadRequestException.class).hasMessage("이미 데이터가 존재합니다.");
        then(betaReservationRepository).should().findByEmail(betaReservationRequest.email());
    }

    private BetaReservation createBetaReservation() {
        return BetaReservation.of("compono@test.com", "compono", "010-1234-5678");
    }

    private BetaReservationRequest createBetaReservationRequest() {
        return BetaReservationRequest.of("compono@test.com", "compono", "010-1234-5678");
    }
}
