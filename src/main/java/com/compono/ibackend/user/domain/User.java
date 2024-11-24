package com.compono.ibackend.user.domain;

import static lombok.AccessLevel.PROTECTED;

import com.compono.ibackend.user.dto.request.UserAddRequest;
import com.compono.ibackend.user.enumType.OauthProvider;
import com.compono.ibackend.user.enumType.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "`users`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //    @Convert(converter = AES256ToStringConverter.class)
    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "nickname", nullable = false, length = 100)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_provider", nullable = false)
    private OauthProvider oauthProvider;

    //    @Convert(converter = AES256ToStringConverter.class)
    @Column(name = "oauth_provider_unique_key")
    private String oauthProviderUniqueKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status", nullable = false)
    private UserStatus userStatus;

    @Column(name = "refresh_token", nullable = true, length = 255)
    private String refreshToken;

    private User(
            String email,
            String nickname,
            OauthProvider oauthProvider,
            String oauthProviderUniqueKey) {
        this.email = email;
        this.nickname = nickname;
        this.oauthProvider = oauthProvider;
        this.oauthProviderUniqueKey = oauthProviderUniqueKey;
        this.userStatus = UserStatus.UNCERTIFIED;
    }

    public void verifyEmail() {
        this.userStatus = UserStatus.ACTIVE;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public static User from(UserAddRequest request) {
        return new User(
                request.email(),
                request.nickname(),
                request.oauthProvider(),
                request.oauthProviderUniqueKey());
    }
}
