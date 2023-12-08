package com.filestore.filestore.it;

import com.filestore.filestore.dto.UserNewDto;
import com.filestore.filestore.rest.AuthRestControllerV1;
import com.filestore.filestore.utils.TestDataUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

public class AuthIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private WebTestClient rest;

    @Test
    void registerTest() {
        UserNewDto userNewDto = TestDataUtils.createUserNewDto();

        rest.post().uri("/api/v1/auth/register")
                .bodyValue(userNewDto)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void loginTest() {
        UserNewDto userNewDto = TestDataUtils.createUserNewDto();

        rest.post().uri("/api/v1/auth/register")
                .bodyValue(userNewDto)
                .exchange()
                .expectStatus().isOk();

        rest.post().uri("/api/v1/auth/login")
                .bodyValue(userNewDto)
                .exchange()
                .expectStatus().isOk();
    }
}
