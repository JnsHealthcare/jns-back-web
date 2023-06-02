package com.jns.backweb.auth;

import com.jns.backweb.auth.application.JwtProvider;
import com.jns.backweb.auth.application.MemberDetailsService;
import com.jns.backweb.auth.util.AuthExtractor;
import com.jns.backweb.common.exception.ErrorCodeAndMessage;
import com.jns.backweb.common.exception.JnsWebApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final MemberDetailsService memberDetailsService;
    private final JwtProvider jwtProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = AuthExtractor.extract(request);

        if(jwtProvider.validateToken(token)) {
            Long memberId = Long.parseLong(jwtProvider.getAudience(token));
            UserDetails userDetails = memberDetailsService.loadMemberById(memberId);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } else {
            throw new JnsWebApplicationException(ErrorCodeAndMessage.INVALID_REQUEST);
        }

        filterChain.doFilter(request, response);
    }
}
