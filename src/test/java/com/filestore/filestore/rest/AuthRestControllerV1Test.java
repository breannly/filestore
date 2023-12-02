package com.filestore.filestore.rest;

import com.filestore.filestore.TestDataUtils;
import com.filestore.filestore.dto.TokenDto;
import com.filestore.filestore.dto.UserDto;
import com.filestore.filestore.dto.UserNewDto;
import com.filestore.filestore.mapper.UserMapper;
import com.filestore.filestore.mapper.UserMapperImpl;
import com.filestore.filestore.security.TokenDetails;
import com.filestore.filestore.service.AuthService;
import com.filestore.filestore.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Import(UserMapperImpl.class)
@TestPropertySource(properties = {"spring.flyway.enabled=false"})
@WebFluxTest(value = AuthRestControllerV1.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
public class AuthRestControllerV1Test {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;
    @MockBean
    private AuthService authService;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void registerTest() {
        UserNewDto userNewDto = TestDataUtils.createUserNewDto();
        Mockito.when(userService.register(Mockito.any())).thenAnswer(answer -> Mono.just(answer.getArgument(0)));

        webTestClient.post().uri("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userNewDto)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(UserDto.class);
    }

    @Test
    public void loginTest() {
        UserNewDto userNewDto = TestDataUtils.createUserNewDto();
        TokenDetails tokenDetails = TokenDetails.builder().token("token").expiresAt(LocalDateTime.now()).build();
        Mockito.when(authService.authenticate(Mockito.anyString(), Mockito.anyString())).thenReturn(Mono.just(tokenDetails));

        webTestClient.post().uri("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userNewDto)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(TokenDto.class);
    }

}