package com.filestore.filestore.it;

import com.filestore.filestore.dto.TokenDto;
import com.filestore.filestore.dto.UserDto;
import com.filestore.filestore.dto.UserNewDto;
import com.filestore.filestore.dto.UserUpdateDto;
import com.filestore.filestore.utils.TestDataUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

public class UserIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private WebTestClient rest;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void findByIdTest() {
        UserNewDto userNewDto = TestDataUtils.createUserNewDto();

        rest.post().uri("/api/v1/auth/register")
                .bodyValue(userNewDto)
                .exchange()
                .expectStatus().isOk();

        TokenDto tokenDto = rest.post().uri("/api/v1/auth/login")
                .bodyValue(userNewDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TokenDto.class)
                .returnResult()
                .getResponseBody();

        String token = "Bearer " + tokenDto.token();

        UserDto userDto = rest.get().uri("/api/v1/users/1")
                .header("Authorization", token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(1L, userDto.id());
        Assertions.assertEquals(userNewDto.username(), userDto.username());
    }

    @Test
    void updateTest() {
        UserNewDto userNewDto = TestDataUtils.createUserNewDto();

        rest.post().uri("/api/v1/auth/register")
                .bodyValue(userNewDto)
                .exchange()
                .expectStatus().isOk();

        TokenDto tokenDto = rest.post().uri("/api/v1/auth/login")
                .bodyValue(userNewDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TokenDto.class)
                .returnResult()
                .getResponseBody();

        String token = "Bearer " + tokenDto.token();

        UserUpdateDto userUpdateDto = TestDataUtils.createUpdateDto();

        UserDto userDto = rest.put().uri("/api/v1/users/1")
                .header("Authorization", token)
                .bodyValue(userUpdateDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(1L, userDto.id());
        Assertions.assertTrue(passwordEncoder.matches(userUpdateDto.password(), userDto.password()));
    }
}
