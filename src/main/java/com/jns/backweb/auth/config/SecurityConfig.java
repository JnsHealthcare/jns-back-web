package com.jns.backweb.auth.config;

import com.jns.backweb.auth.TokenAuthenticationFilter;
import com.jns.backweb.auth.application.CookieOauthAuthorizationRequestRepository;
import com.jns.backweb.auth.application.OAuthService;
import com.jns.backweb.auth.handler.OAuthLoginFailHandler;
import com.jns.backweb.auth.handler.OAuthLoginSuccessHandler;
import com.jns.backweb.auth.handler.RestAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuthService oAuthService;
    private final OAuthLoginSuccessHandler oAuthLoginSuccessHandler;
    private final OAuthLoginFailHandler oAuthLoginFailHandler;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final CookieOauthAuthorizationRequestRepository cookieOauthAuthorizationRequestRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors().and()
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .exceptionHandling()
                    .authenticationEntryPoint(restAuthenticationEntryPoint).and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers("/", "/error/*", "/favicon.ico").permitAll()
                    .antMatchers("/auth/**", "/oauth2/callback/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                    .authorizationEndpoint()
                    .authorizationRequestRepository(cookieOauthAuthorizationRequestRepository)
                .and()
                .redirectionEndpoint()
                    .baseUri("/oauth2/callback/*")
                .and()
                .userInfoEndpoint()
                    .userService(oAuthService)
                .and()
                .successHandler(oAuthLoginSuccessHandler)
                .failureHandler(oAuthLoginFailHandler)
                .and()
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();


    }


}
