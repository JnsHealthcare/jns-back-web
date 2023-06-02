package com.jns.backweb.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jns.backweb.common.dto.ApiResponse;
import com.jns.backweb.common.exception.ErrorCodeAndMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException {
       log.error("[auth error(uri={})] {}", httpServletRequest.getRequestURI(), e.getMessage());

        String error = objectMapper.writeValueAsString(ApiResponse.error(ErrorCodeAndMessage.UNAUTHORIZED));

        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.getWriter().write(error);
    }
}
