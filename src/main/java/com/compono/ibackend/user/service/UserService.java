package com.compono.ibackend.user.service;

import com.compono.ibackend.user.domain.User;
import com.compono.ibackend.user.dto.UserAddRequestDTO;
import com.compono.ibackend.user.dto.UserAddResponseDTO;
import com.compono.ibackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserAddResponseDTO addUser(UserAddRequestDTO dto) {
        if (userRepository.existsUserByOauthProviderUniqueKey(dto.oauthProviderUniqueKey())) {
            throw new RuntimeException("이미 해당 SNS 로그인으로 가입된 유저입니다.");
        }
        User user = new User(dto);
        userRepository.save(user);
        return new UserAddResponseDTO("added");
    }
}
