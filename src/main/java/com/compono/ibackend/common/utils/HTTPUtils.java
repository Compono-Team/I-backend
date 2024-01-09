package com.compono.ibackend.common.utils;

import com.compono.ibackend.common.enumType.ErrorCode;
import com.compono.ibackend.common.exception.CustomException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class HTTPUtils {

    /**
     * GET 요청을 보내는 함수
     *
     * @param url 요청을 보낼 API path
     * @param header
     * @param className 요청 시, 응답을 받을 Class type
     * @return
     */
    public static <T> ResponseEntity<T> get(
            UriComponentsBuilder url, HttpHeaders header, Class<T> className) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<?> entity = new HttpEntity<>(header);

            ResponseEntity<T> response =
                    restTemplate.exchange(url.toUriString(), HttpMethod.GET, entity, className);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.OPEN_API_REQUEST_FAIL);
            }

            return response;
        } catch (Exception ex) {
            throw new CustomException(ex);
        }
    }

    /**
     * POST 요청을 보내는 함수
     *
     * @param url 요청을 보낼 API path
     * @param headers
     * @param body
     * @param className 요청 시, 응답을 받을 Class type
     * @return
     */
    public static <T> ResponseEntity<T> post(
            String url, HttpHeaders headers, Object body, Class<T> className) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        ResponseEntity<T> response = restTemplate.postForEntity(url, entity, className);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.OPEN_API_REQUEST_FAIL);
        }

        return response;
    }
}
