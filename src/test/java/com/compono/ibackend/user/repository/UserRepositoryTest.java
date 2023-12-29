package com.compono.ibackend.user.repository;

import com.compono.ibackend.user.domain.User;
import com.compono.ibackend.user.dto.UserAddRequestDTO;
import com.compono.ibackend.user.enumType.OauthProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void existsUserByOauthProviderUniqueKey() {
        UserAddRequestDTO dto = new UserAddRequestDTO("test@gmail.com", "tester", OauthProvider.GOOGLE, "1234");
        User user = new User(dto);
        userRepository.save(user);

        Assertions.assertThat(userRepository.existsUserByOauthProviderUniqueKey("1234")).isTrue();
    }
}