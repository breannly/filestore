package com.filestore.filestore.service.impl;

import com.filestore.filestore.entity.User;
import com.filestore.filestore.exception.AuthException;
import com.filestore.filestore.exception.ObjectNotFoundException;
import com.filestore.filestore.repository.UserRepository;
import com.filestore.filestore.security.TokenDetails;
import com.filestore.filestore.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final String secret;
    private final Integer expiration;

    @Override
    public Mono<TokenDetails> authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .flatMap(user -> {
                    if (passwordEncoder.matches(password, user.getPassword())) {
                        TokenDetails tokenDetails = generateToken(user);
                        tokenDetails.setUserId(user.getId());
                        return Mono.just(tokenDetails);
                    }
                    return Mono.error(new AuthException("Invalid username or password"));
                })
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("User not found")));
    }

    private TokenDetails generateToken(User user) {
        Map<String, Object> claims = new HashMap<>() {{
            put("role", user.getRole());
            put("username", user.getUsername());
        }};
        return generateToken(claims, user.getId().toString());
    }

    private TokenDetails generateToken(Map<String, Object> claims, String subject) {
        Date issuedAt = new Date();
        Date expirationDate = new Date(issuedAt.getTime() + expiration * 1000L);

        try {
            String token = Jwts.builder()
                    .claims(claims)
                    .subject(subject)
                    .issuedAt(issuedAt)
                    .expiration(expirationDate)
                    .signWith(Keys.hmacShaKeyFor(secret.getBytes()), Jwts.SIG.HS256)
                    .compact();

            return TokenDetails.builder()
                    .token(token)
                    .issuedAt(LocalDateTime.ofInstant(issuedAt.toInstant(), ZoneId.systemDefault()))
                    .expiresAt(LocalDateTime.ofInstant(expirationDate.toInstant(), ZoneId.systemDefault()))
                    .build();
        } catch (Exception e) {
            throw new AuthException("Failed to generate the token");
        }
    }
}
