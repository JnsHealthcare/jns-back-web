package com.jns.backweb.auth.application;

import com.jns.backweb.common.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;
    private final JwtParser jwtParser;

    public JwtProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret()
                .getBytes(StandardCharsets.UTF_8));
        this.jwtParser = Jwts.parserBuilder()
                .requireIssuer(jwtProperties.getIssuer())
                .setSigningKey(secretKey)
                .build();
    }

    public String generateAccessToken(Long memberId) {
        return generateToken(memberId,
                jwtProperties.getAccessTokenSubject(),
                jwtProperties.getAccessTokenDuration());
    }

    public String generateRefreshToken(Long memberId) {
        return generateToken(memberId,
                jwtProperties.getRefreshTokenSubject(),
                jwtProperties.getRefreshTokenDuration());
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = jwtParser.parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException | NullPointerException e) {
            return false;
        }
    }

    public String getAudience(String token) {
        return jwtParser.parseClaimsJws(token).getBody().getAudience();
    }

    public String getTokenType() {
        return jwtProperties.getTokenType();
    }


    private String generateToken(Long memberId, String subject, Long expireTime) {
        long now = System.currentTimeMillis();
        Date expiration = new Date(now + expireTime);

        return Jwts.builder()
                .setIssuer(jwtProperties.getIssuer())
                .setSubject(subject)
                .setAudience(String.valueOf(memberId))
                .setExpiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    public long getRefreshTokenDuration() {
        return jwtProperties.getRefreshTokenDuration();
    }
}
