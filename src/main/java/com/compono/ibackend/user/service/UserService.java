package com.compono.ibackend.user.service;

import com.compono.ibackend.common.enumType.ErrorCode;
import com.compono.ibackend.common.exception.CustomException;
import com.compono.ibackend.common.exception.DuplicateResourceException;
import com.compono.ibackend.user.domain.User;
import com.compono.ibackend.user.dto.request.UserAddRequest;
import com.compono.ibackend.user.dto.response.UserAddResponse;
import com.compono.ibackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserAddResponse addUser(UserAddRequest request) {
        User user = request.toEntity();
        userRepository
                .findByEmail(user.getEmail())
                .ifPresent(
                        u -> {
                            throw new DuplicateResourceException(
                                    ErrorCode.DUPLICATED_FAILED, "email");
                        });
        userRepository.save(user);
        return UserAddResponse.from(user);
    }

    @Transactional
    public void verifyEmail(String email, String code) {
        // TODO: user 검증 로직
        findUserByEmail(email).verifyEmail();
    }

    /**
     * email로 User 찾는 함수
     *
     * @param email
     * @return
     */
    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(
                        () ->
                                new CustomException(
                                        HttpStatus.BAD_REQUEST, ErrorCode.NOT_FOUND_USER_EMAIL));
    }

    @Transactional(readOnly = true)
    public void resendVerifyEmail(String email) {
        // TODO: 실제 이메일 발송 로직
    }
}
