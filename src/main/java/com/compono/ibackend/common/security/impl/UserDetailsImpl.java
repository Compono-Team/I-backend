package com.compono.ibackend.common.security.impl;

import com.compono.ibackend.user.domain.User;
import io.jsonwebtoken.Claims;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class UserDetailsImpl implements UserDetails {

    private final Long id;
    private final String email;

    public UserDetailsImpl(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public UserDetailsImpl(Claims claims) {
        this.id = claims.get("id", Long.class);
        this.email = claims.getSubject();
    }

    public UserDetailsImpl(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
