package com.compono.ibackend.user.domain;

import com.compono.ibackend.user.dto.UserAddRequestDTO;
import com.compono.ibackend.user.enumType.OauthProvider;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "`user`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OauthProvider oauthProvider;

    @Column(nullable = false)
    private String oauthProviderUniqueKey;

    @Column(nullable = false)
    private Boolean isAuthenticated;

    public User(UserAddRequestDTO dto) {
        this.email = dto.email();
        this.nickname = dto.nickname();
        this.oauthProvider = dto.oauthProvider();
        this.oauthProviderUniqueKey = dto.oauthProviderUniqueKey();
        this.isAuthenticated = false;
    }
}
