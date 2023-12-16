package com.compono.ibackend.common.utils;

import com.compono.ibackend.common.enumType.ErrorCode;
import com.compono.ibackend.common.exception.CustomException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class HTTPUtils {
    /**
     * GET 요청을 보내는 함수
     *
     * @param url       요청을 보낼 API path
     * @param header
     * @param className 요청 시, 응답을 받을 Class type
     * @return
     */
    public static <T> ResponseEntity<T> get(String url, HttpHeaders header, Class<T> className) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity entity = new HttpEntity(header);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

        ResponseEntity<T> response = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.GET, entity, className
        );

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.OPEN_API_REQUEST_FAIL);
        }

        return response;
    }

    /**
     * POST 요청을 보내는 함수
     *
     * @param url       요청을 보낼 API path
     * @param header
     * @param body
     * @param className 요청 시, 응답을 받을 Class type
     * @return
     */
    public static <T> ResponseEntity<T> post(String url, HttpHeaders headers, String body,
                                             Class<T> className) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<T> response = restTemplate.postForEntity(url, entity, className);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorCode.OPEN_API_REQUEST_FAIL);
        }

        return response;
    }
}
