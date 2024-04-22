package com.compono.ibackend.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

import com.compono.ibackend.common.enumType.ErrorCode;
import com.compono.ibackend.common.exception.CustomException;
import com.compono.ibackend.user.domain.User;
import com.compono.ibackend.user.dto.request.UserAddRequest;
import com.compono.ibackend.user.dto.response.UserAddResponse;
import com.compono.ibackend.user.enumType.OauthProvider;
import com.compono.ibackend.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepository;

    @InjectMocks private UserService userService;

    @Test
    void addUser() {
        String email = "test@gmail.com";
        String nickname = "tester";
        OauthProvider oauthProvider = OauthProvider.KAKAO;
        UserAddRequest request = new UserAddRequest(email, nickname, oauthProvider, null, true);

        when(userRepository.save(any(User.class))).thenReturn(createUser(request));

        UserAddResponse response = userService.addUser(request);

        assertThat(response.email()).isEqualTo(email);
    }

    @DisplayName("[정상 케이스] email에 대한 User를 조회한다.")
    @Test
    void findUserByEmail() {
        String email = "test@gmail.com";
        UserAddRequest request = createUserAddRequest(email);
        User user = createUser(request);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User foundUser = userService.findUserByEmail(email);

        assertNotNull(foundUser);
        assertEquals("", user, foundUser);
    }

    @DisplayName("[오류 케이스 - NOT_FOUND_USER_EMAIL] email에 대한 User를 조회한다.")
    @Test
    void findUserByEmail_notFoundUserEmail() {
        String email = "test@gmail.com";
        UserAddRequest request = createUserAddRequest(email);
        User user = createUser(request);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        CustomException ex =
                assertThrows(CustomException.class, () -> userService.findUserByEmail(email));
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND_USER_EMAIL);
    }

    private User createUser(UserAddRequest request) {
        return User.from(request);
    }

    private UserAddRequest createUserAddRequest(String email) {
        String nickname = "tester";
        OauthProvider oauthProvider = OauthProvider.KAKAO;
        return new UserAddRequest(email, nickname, oauthProvider, null, true);
    }
}
