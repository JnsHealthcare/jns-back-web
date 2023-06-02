package com.jns.backweb.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jns.backweb.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException {
       log.error("Responding with unauthorized error. Message - {}", e.getMessage());

        String error = objectMapper.writeValueAsString(ApiResponse.error("EDD", e.getMessage()));
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, error);
    }
}
