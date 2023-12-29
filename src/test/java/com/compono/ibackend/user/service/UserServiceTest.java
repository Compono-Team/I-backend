package com.compono.ibackend.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.compono.ibackend.user.domain.User;
import com.compono.ibackend.user.dto.UserAddRequestDTO;
import com.compono.ibackend.user.dto.UserAddResponseDTO;
import com.compono.ibackend.user.enumType.OauthProvider;
import com.compono.ibackend.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Test
    void addUser() {
        UserAddRequestDTO dto = new UserAddRequestDTO("test@gmail.com", "tester", OauthProvider.GOOGLE, "1234");
        User user = new User(dto);
        when(userRepository.save(any())).thenReturn(user);

        UserAddResponseDTO res = userService.addUser(dto);
        assertThat(res.message()).isEqualTo("added");
    }
}