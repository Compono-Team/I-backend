package com.compono.ibackend.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithUserPrincipalsSecurityContextFactory.class)
public @interface WithUserPrincipals {

    String id() default "1";

    String email() default "compono@test.com";
}
