package com.compono.ibackend.annotation;

import com.compono.ibackend.common.security.impl.UserDetailsImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithUserPrincipalsSecurityContextFactory
        implements WithSecurityContextFactory<WithUserPrincipals> {

    @Override
    public SecurityContext createSecurityContext(WithUserPrincipals annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UserDetailsImpl userDetails =
                new UserDetailsImpl(Long.valueOf(annotation.id()), annotation.email());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails, "", userDetails.getAuthorities());
        context.setAuthentication(usernamePasswordAuthenticationToken);
        return context;
    }
}
