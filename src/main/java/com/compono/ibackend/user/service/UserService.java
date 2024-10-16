package com.compono.ibackend.user.service;

import com.compono.ibackend.common.enumType.ErrorCode;
import com.compono.ibackend.common.exception.CustomException;
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
        // TODO request validation
        User user = request.toEntity();
        userRepository.save(user);
        return UserAddResponse.from(user);
    }

    /**
     * emaill로 User 찾는 함수
     *
     * @param email
     * @return
     */
    public User findUserByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(
                        () ->
                                new CustomException(
                                        HttpStatus.BAD_REQUEST, ErrorCode.NOT_FOUND_USER_EMAIL));
    }
}
