package com.filestore.filestore.config;

import com.filestore.filestore.config.property.JwtProperty;
import com.filestore.filestore.entity.UserRole;
import com.filestore.filestore.security.BearerTokenServerAuthenticationConverter;
import com.filestore.filestore.security.JwtHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final Map<HttpMethod, String[]> publicEndpoints = Map.of(
            HttpMethod.POST, new String[]{"/api/v1/auth/login", "/api/v1/auth/register"}
    );

    private final Map<HttpMethod, String[]> mutualEndpoints = Map.of(
            HttpMethod.POST, new String[]{"/api/v1/users/{userId}/files"},
            HttpMethod.PUT, new String[]{"/api/v1/users/{userId}", "/api/v1/users/{userId}/files/{fileId}"},
            HttpMethod.GET, new String[]{"/api/v1/users/{userId}", "/api/v1/users/{userId}/files/{fileId}"},
            HttpMethod.DELETE, new String[]{"/api/v1/users/{userId}", "/api/v1/users/{userId}/files/{fileId}"}
    );

    private final String[] allRoles = {UserRole.USER.name(), UserRole.ADMIN.name()};

    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        return Mono::just;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         JwtProperty jwtProperty,
                                                         ReactiveAuthenticationManager authenticationManager) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.POST, publicEndpoints.get(HttpMethod.POST)).permitAll()
                        .pathMatchers(HttpMethod.POST, mutualEndpoints.get(HttpMethod.POST)).hasAnyAuthority(allRoles)
                        .pathMatchers(HttpMethod.PUT, mutualEndpoints.get(HttpMethod.PUT)).hasAnyAuthority(allRoles)
                        .pathMatchers(HttpMethod.GET, mutualEndpoints.get(HttpMethod.GET)).hasAnyAuthority(allRoles)
                        .pathMatchers(HttpMethod.DELETE, mutualEndpoints.get(HttpMethod.DELETE)).hasAnyAuthority(allRoles)
                        .anyExchange().hasAuthority(UserRole.ADMIN.name())
                )
                .addFilterAt(bearerAuthenticationFilter(jwtProperty.getSecret(), authenticationManager), SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }

    private AuthenticationWebFilter bearerAuthenticationFilter(String secret,
                                                               ReactiveAuthenticationManager authenticationManager) {

        AuthenticationWebFilter bearerAuthenticationFilter = new AuthenticationWebFilter(authenticationManager);
        bearerAuthenticationFilter.setServerAuthenticationConverter(new BearerTokenServerAuthenticationConverter(new JwtHandler(secret)));
        bearerAuthenticationFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/**"));

        return bearerAuthenticationFilter;
    }
}
