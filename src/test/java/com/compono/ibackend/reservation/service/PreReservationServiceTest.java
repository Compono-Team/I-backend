package com.compono.ibackend.reservation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.compono.ibackend.common.exception.BadRequestException;
import com.compono.ibackend.reservation.domain.PreReservation;
import com.compono.ibackend.reservation.dto.request.PreReservationRequest;
import com.compono.ibackend.reservation.dto.response.PreReservationResponse;
import com.compono.ibackend.reservation.repository.PreReservationRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("[비즈니스 로직] - 사전예약")
@ExtendWith(MockitoExtension.class)
class PreReservationServiceTest {

    @InjectMocks private PreReservationService preReservationService;

    @Mock private PreReservationRepository preReservationRepository;

    @DisplayName("사전예약 정보를 정상적으로 생성한다.")
    @Test
    void givenPreReservationRequest_whenAddPreReservation_thenPreReservationResponse() {
        // Given
        PreReservationRequest preReservationRequest = createPreReservationRequest();
        PreReservation preReservation = createPreReservation();
        given(preReservationRepository.save(any(PreReservation.class))).willReturn(preReservation);

        // When
        PreReservationResponse preReservationResponse =
                preReservationService.addPreReservation(preReservationRequest);

        // Then
        assertThat(preReservationResponse)
                .isNotNull()
                .hasFieldOrPropertyWithValue("email", preReservationRequest.email())
                .hasFieldOrPropertyWithValue("name", preReservationRequest.name())
                .hasFieldOrPropertyWithValue("phoneNumber", preReservationRequest.phoneNumber())
                .hasFieldOrPropertyWithValue("expectation", preReservationRequest.expectation());
        then(preReservationRepository).should().save(any(PreReservation.class));
    }

    @DisplayName("사전예약 등록 시, 중복된 이메일이 존재하면 에외를 던진다.")
    @Test
    void givenDuplicatedEmail_whenAddPreReservation_thenThrowsBadRequestException() {
        // Given
        PreReservationRequest preReservationRequest = createPreReservationRequest();
        PreReservation preReservation = createPreReservation();

        Long preReservationId = 0L;
        given(preReservationRepository.findByEmail(preReservationRequest.email()))
                .willReturn(Optional.of(preReservation));

        // When
        Throwable t =
                catchThrowable(
                        () -> preReservationService.addPreReservation(preReservationRequest));

        // Then
        assertThat(t).isInstanceOf(BadRequestException.class).hasMessage("이미 데이터가 존재합니다.");
        then(preReservationRepository).should().findByEmail(preReservationRequest.email());
    }

    @DisplayName("사전예약 단건 조회를 정상적으로 반환한다.")
    @Test
    void givenPreReservationId_whenFindPreReservationWithId_thenPreReservationResponse() {
        // Given
        Long preReservationId = 1L;
        PreReservation preReservation = createPreReservation();
        given(preReservationRepository.findById(preReservationId))
                .willReturn(Optional.of(preReservation));

        // When
        PreReservationResponse preReservationResponse =
                preReservationService.findPreReservation(preReservationId);

        // Then
        assertThat(preReservationResponse)
                .isNotNull()
                .hasFieldOrPropertyWithValue("email", preReservation.getEmail())
                .hasFieldOrPropertyWithValue("name", preReservation.getName())
                .hasFieldOrPropertyWithValue("phoneNumber", preReservation.getPhoneNumber())
                .hasFieldOrPropertyWithValue("expectation", preReservation.getExpectation());
        then(preReservationRepository).should().findById(preReservationId);
    }

    @DisplayName("사전예약 단건 조회 시, 정보가 없으면 예외를 던진다.")
    @Test
    void
            givenNonexistentPreReservationId_whenFindPreReservationWithId_thenThrowsBadRequestException() {
        // Given
        Long preReservationId = 0L;
        given(preReservationRepository.findById(preReservationId)).willReturn(Optional.empty());

        // When
        Throwable t =
                catchThrowable(() -> preReservationService.findPreReservation(preReservationId));

        // Then
        assertThat(t).isInstanceOf(BadRequestException.class).hasMessage("요청한 사전예약 정보가 존재하지 않습니다.");
        then(preReservationRepository).should().findById(preReservationId);
    }

    private PreReservation createPreReservation() {
        return PreReservation.of("compono@test.com", "compono", "010-1234-1234", "바라는 점은..");
    }

    private PreReservationRequest createPreReservationRequest() {
        return PreReservationRequest.of("compono@test.com", "compono", "010-1234-1234", "바라는 점은..");
    }
}
