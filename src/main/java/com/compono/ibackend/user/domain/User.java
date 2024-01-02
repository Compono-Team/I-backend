package com.compono.ibackend.user.domain;

import static lombok.AccessLevel.PROTECTED;

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
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "`user`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "nickname", nullable = false, length = 100)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_provider", nullable = false)
    private OauthProvider oauthProvider;

    @Column(name = "oauth_provider_unique_key")
    private String oauthProviderUniqueKey;

    @Column(name = "is_authenticated", nullable = false)
    private Boolean isAuthenticated;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status", nullable = false)
    private UserStatus userStatus;
}
