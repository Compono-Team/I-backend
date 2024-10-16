package com.compono.ibackend.common.fixtures;

import com.compono.ibackend.user.domain.User;
import com.compono.ibackend.user.dto.request.UserAddRequest;
import com.compono.ibackend.user.enumType.OauthProvider;
import java.util.UUID;

public class UserFixtures {

    public static User COMPONO_USER() {
        UserAddRequest request =
                new UserAddRequest(
                        "test@test.com",
                        "compono",
                        OauthProvider.KAKAO,
                        UUID.randomUUID().toString(),
                        true);
        return request.toEntity();
    }
}
