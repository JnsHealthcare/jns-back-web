package com.jns.backweb.auth;

import com.jns.backweb.auth.application.JwtProvider;
import com.jns.backweb.auth.util.AuthExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(CorsUtils.isCorsRequest(request)) {
            log.debug("[CORS Request] URI={}", request.getRequestURI());
        };
        Optional<String> optionalToken = AuthExtractor.extract(request);

        if(optionalToken.isEmpty()) {
            filterChain.doFilter(request, response);
        }

    }
}
