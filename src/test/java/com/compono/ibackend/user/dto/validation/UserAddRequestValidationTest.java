package com.compono.ibackend.user.dto.validation;

import com.compono.ibackend.user.dto.UserAddRequestDTO;
import com.compono.ibackend.user.enumType.OauthProvider;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserAddRequestValidationTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void validation_email() {
        UserAddRequestDTO emailInvalidRequest = new UserAddRequestDTO("test", "tester", OauthProvider.GOOGLE, "1234");
        Set<ConstraintViolation<UserAddRequestDTO>> validate =
                validator.validate(emailInvalidRequest);
        Assertions.assertThat(validate.size()).isEqualTo(1);
    }

    @Test
    void validation_nickname() {
        UserAddRequestDTO nicknameInvalidRequest1 = new UserAddRequestDTO("test@gmail.com", "", OauthProvider.GOOGLE,
                "1234");
        UserAddRequestDTO nicknameInvalidRequest2 = new UserAddRequestDTO("test@gmail.com", null, OauthProvider.GOOGLE,
                "1234");

        Set<ConstraintViolation<UserAddRequestDTO>> validate1 =
                validator.validate(nicknameInvalidRequest1);
        Set<ConstraintViolation<UserAddRequestDTO>> validate2 =
                validator.validate(nicknameInvalidRequest2);

        Assertions.assertThat(validate1.size()).isEqualTo(1);
        Assertions.assertThat(validate2.size()).isEqualTo(1);
    }
}
