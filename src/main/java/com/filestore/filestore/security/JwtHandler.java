package com.filestore.filestore.security;

import com.filestore.filestore.exception.AuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

public class JwtHandler {

    private final String secret;

    public JwtHandler(String secret) {
        this.secret = secret;
    }

    public Mono<VerificationResult> check(String accessToken) {
        return Mono.just(verify(accessToken))
                .onErrorResume(e -> Mono.error(new AuthException(e.getMessage())));
    }

    private VerificationResult verify(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return new VerificationResult(claims, token);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static class VerificationResult {
        public Claims claims;
        public String token;

        public VerificationResult(Claims claims, String token) {
            this.claims = claims;
            this.token = token;
        }
    }
}
