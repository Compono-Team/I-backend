package com.compono.ibackend.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.compono.ibackend.user.domain.User;
import com.compono.ibackend.user.dto.request.UserAddRequest;
import com.compono.ibackend.user.dto.response.UserAddResponse;
import com.compono.ibackend.user.enumType.OauthProvider;
import com.compono.ibackend.user.repository.UserRepository;
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

    private User createUser(UserAddRequest request) {
        return User.from(request);
    }
}
