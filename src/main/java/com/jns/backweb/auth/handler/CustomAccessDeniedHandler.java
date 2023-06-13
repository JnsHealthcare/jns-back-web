package com.jns.backweb.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jns.backweb.common.dto.ApiResponse;
import com.jns.backweb.common.exception.ErrorCodeAndMessage;
import com.jns.backweb.common.exception.JnsWebApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (response.isCommitted()) {
            log.debug("[Response already committed] RequestURI={}", request.getRequestURI());
            return;
        }

        log.debug("[Access denied] RequestURI={}", request.getRequestURI());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String error = objectMapper.writeValueAsString(ApiResponse.error(ErrorCodeAndMessage.UNAUTHORIZED));
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(error);
    }
}
