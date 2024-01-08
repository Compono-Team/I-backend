package com.compono.ibackend.common.utils.cryptography;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("[Utils] AES256 알고리즘 암호화/복호화 검사")
@SpringBootTest(
        classes = {AES256Utils.class},
        properties = {"spring.cryptography.aes.secret-key=12114567890121556789012326673001"})
class AES256UtilsTest {

    private static Stream<Arguments> checkEncryptAndDecrypt() {
        return Stream.of(arguments("010-1234-5678"), arguments("admin@ibackend.com"));
    }

    @DisplayName("유효한 키로 암호화와 복호화 검사하면 true를 반환합니다.")
    @ParameterizedTest
    @MethodSource("checkEncryptAndDecrypt")
    void testEncryptAndDecrypt(String value) {
        // 평문을 암호화
        String encryptedText = AES256Utils.encryptAES256(value);

        // 암호문을 복호화
        String decryptedText = AES256Utils.decryptAES256(encryptedText);

        // 복호화된 텍스트가 원래의 평문과 일치하는지 확인
        assertEquals(value, decryptedText);
    }
}
