package com.springboot3.template.utils;

import com.springboot3.template.constants.ApiConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-token-validity}")
    private Long accessTokenValidity;
    private SecretKey key;

    @PostConstruct
    private void init() {
        key = Jwts.SIG.HS256.key().random(new SecureRandom(secretKey.getBytes(StandardCharsets.UTF_8))).build();
    }

    public String createToken(String userName, Map<String, Object> claims) {

        return Jwts.builder()
                .subject(userName)
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(accessTokenValidity)))
                .signWith(key)
                .compact();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        String token = resolveToken(req);
        return parseJwtClaims(token);
    }

    public Claims parseJwtClaims(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(ApiConstant.TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(ApiConstant.TOKEN_PREFIX)) {
            return bearerToken.substring(ApiConstant.TOKEN_PREFIX.length());
        }
        return null;
    }

    public Authentication createAuthentication(String token) {
        Claims claims = parseJwtClaims(token);
        String username = claims.getSubject();
        return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
    }
}
