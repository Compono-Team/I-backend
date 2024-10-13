package com.compono.ibackend.factory;

import com.compono.ibackend.model.user.domain.User;
import com.compono.ibackend.model.user.dto.request.UserAddRequest;
import com.compono.ibackend.model.user.enumType.OauthProvider;
import java.util.UUID;

public class UserFactory {

    public static User createUser(String email) {
        UserAddRequest request =
                new UserAddRequest(
                        email, "compono", OauthProvider.KAKAO, UUID.randomUUID().toString(), true);
        return User.from(request);
    }
}
