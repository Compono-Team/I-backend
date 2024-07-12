package com.compono.ibackend.reservation.dto.request;

import com.compono.ibackend.common.annotation.Phone;
import com.compono.ibackend.reservation.domain.BetaReservation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record BetaReservationRequest(
        @NotBlank(message = "이메일은 공백이 될 수 없습니다.") @Email(message = "올바른 형식의 이메일을 입력하세요.")
                String email,
        @NotBlank(message = "이름은 공백이 될 수 없습니다.") String name,
        @NotBlank(message = "전화번호는 공백이 될 수 없습니다.") @Phone(message = "올바른 형식의 전화번호를 입력하세요.")
                String phoneNumber) {

    public BetaReservation toEntity() {
        return BetaReservation.of(email, name, phoneNumber);
    }

    public static BetaReservationRequest of(String email, String name, String phoneNumber) {
        return new BetaReservationRequest(email, name, phoneNumber);
    }
}
