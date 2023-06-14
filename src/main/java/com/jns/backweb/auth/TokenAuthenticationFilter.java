package com.jns.backweb.auth;

import com.jns.backweb.auth.application.JwtProvider;
import com.jns.backweb.auth.application.MemberDetailsService;
import com.jns.backweb.auth.model.LoginMember;
import com.jns.backweb.auth.util.AuthExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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

    private final MemberDetailsService memberDetailsService;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(CorsUtils.isCorsRequest(request)) {
            log.debug("[CORS Request] URI={}", request.getRequestURI());
        };
        Optional<String> optionalToken = AuthExtractor.extract(request);

        if(optionalToken.isEmpty()) {
            LoginMember visitor = LoginMember.createVisitor();
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(visitor, null, visitor.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } else if (jwtProvider.validateToken(optionalToken.get())) {
            Long memberId = Long.parseLong(jwtProvider.getAudience(optionalToken.get()));
            UserDetails userDetails = memberDetailsService.loadMemberById(memberId);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } else {
            log.debug("[Invalid token] requestURI = {}", request.getRequestURI());
        }

        filterChain.doFilter(request, response);
    }
}
