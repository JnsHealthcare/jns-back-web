package com.jns.backweb.auth.config;

import com.jns.backweb.auth.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
@RequiredArgsConstructor
public class JwtConfig {

    private final JwtProperties jwtProperties;

}
