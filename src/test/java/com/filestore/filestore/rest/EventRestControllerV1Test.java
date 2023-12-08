package com.filestore.filestore.rest;

import com.filestore.filestore.utils.TestDataUtils;
import com.filestore.filestore.dto.UserDto;
import com.filestore.filestore.entity.Event;
import com.filestore.filestore.mapper.EventMapper;
import com.filestore.filestore.mapper.EventMapperImpl;
import com.filestore.filestore.service.EventService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@Import(EventMapperImpl.class)
@TestPropertySource(properties = {"spring.flyway.enabled=false"})
@WebFluxTest(value = EventRestControllerV1.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
public class EventRestControllerV1Test {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private EventService eventService;

    @Autowired
    private EventMapper eventMapper;

    @Test
    public void getTest() {
        Event event = TestDataUtils.createEvent();
        Mockito.when(eventService.findById(Mockito.anyLong())).thenReturn(Mono.just(event));

        webTestClient.get().uri("/api/v1/events/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(UserDto.class);
    }

    @Test
    public void deleteTest() {
        Event event = TestDataUtils.createEvent();
        Mockito.when(eventService.delete(Mockito.anyLong())).thenReturn(Mono.just(event));

        webTestClient.delete().uri("/api/v1/events/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(UserDto.class);
    }

}