package com.compono.ibackend.common.utils.cryptography;

import com.compono.ibackend.common.enumType.ErrorCode;
import com.compono.ibackend.common.exception.CustomException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AES256Utils {

    private static final String ALGORITHM_AES_KEY = "AES";
    private static final String TRANSFORMATION_AES_KEY = "AES/CBC/PKCS5PADDING";
    private static final Base64.Decoder DECODER_BASE64 = Base64.getDecoder();
    private static final Base64.Encoder ENCODER_BASE64 = Base64.getEncoder();
    private static String secretKeyAES;

    @Value("${spring.cryptography.aes.secret-key}")
    private void setSecretKeyAES(String secretKeyAES) {
        this.secretKeyAES = secretKeyAES;
    }

    @SneakyThrows
    public static String encryptAES256(String plainText) {
        Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);

        byte[] encryptedByte = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        return ENCODER_BASE64.encodeToString(encryptedByte);
    }

    @SneakyThrows
    public static String decryptAES256(String cipherText) {
        Cipher cipher = getCipher(Cipher.DECRYPT_MODE);

        byte[] encryptedByte = DECODER_BASE64.decode(cipherText);
        byte[] decryptedByte = cipher.doFinal(encryptedByte);

        return new String(decryptedByte, StandardCharsets.UTF_8);
    }

    /**
     * @param decryptMode 암호화 모드
     * @return
     */
    private static Cipher getCipher(int decryptMode)
        throws InvalidAlgorithmParameterException, InvalidKeyException {
        String encodedIV = getEncodedIV();

        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION_AES_KEY);
            IvParameterSpec ivSpec = new IvParameterSpec(DECODER_BASE64.decode(encodedIV));

            cipher.init(decryptMode, getAESKeySpec(), ivSpec);
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.CRYPTOGRAPHY_FAILED);
        }
    }

    private static SecretKeySpec getAESKeySpec() {
        byte[] key = DECODER_BASE64.decode(secretKeyAES);
        SecretKeySpec keySpec = new SecretKeySpec(key, ALGORITHM_AES_KEY);

        return keySpec;
    }

    private static String getEncodedIV() {
        return ENCODER_BASE64.encodeToString(secretKeyAES.substring(0, 16).getBytes());
    }
}
