package com.compono.ibackend.reservation.dto.request;

import com.compono.ibackend.common.annotation.Phone;
import com.compono.ibackend.reservation.domain.PreReservation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PreReservationRequest(
        @NotBlank(message = "이메일은 공백이 될 수 없습니다.") @Email(message = "올바른 형식의 이메일을 입력하세요.")
                String email,
        @NotBlank(message = "이름은 공백이 될 수 없습니다.") String name,
        @NotBlank(message = "전화번호는 공백이 될 수 없습니다.") @Phone(message = "올바른 형식의 전화번호를 입력하세요.")
                String phoneNumber,
        @Size(max = 1000, message = "최대 1000자 까지 가능합니다.") String expectation) {
    public PreReservation toEntity() {
        return PreReservation.of(email, name, phoneNumber, expectation);
    }

    public static PreReservationRequest of(
            String email, String name, String phoneNumber, String expectation) {
        return new PreReservationRequest(email, name, phoneNumber, expectation);
    }
}
