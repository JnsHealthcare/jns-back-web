package com.jns.backweb.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
@RequiredArgsConstructor
@Getter
public class JwtProperties {

    private final String secret;
    private final String issuer;
    private final String accessTokenSubject;
    private final String refreshTokenSubject;
    private final Long accessTokenDuration;
    private final Long refreshTokenDuration;
    private final String tokenType;


}
