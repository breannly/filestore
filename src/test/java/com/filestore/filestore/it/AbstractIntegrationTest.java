package com.filestore.filestore.it;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MySQLContainer;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest {

    @Autowired
    private ApplicationContext context;

    public static MySQLContainer<?> container = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("filestore")
            .withUsername("root")
            .withPassword("root");

    @BeforeAll
    static void beforeAll() {
        container.start();

        String r2dbcUrl = String.format("r2dbc:mysql://%s:%d/%s", container.getHost(), container.getFirstMappedPort(), container.getDatabaseName());

        System.setProperty("spring.r2dbc.url", r2dbcUrl);
        System.setProperty("spring.r2dbc.username", container.getUsername());
        System.setProperty("spring.r2dbc.password", container.getPassword());
        System.setProperty("spring.flyway.url", container.getJdbcUrl());
        System.setProperty("spring.flyway.user", container.getUsername());
        System.setProperty("spring.flyway.password", container.getPassword());
    }

    @AfterAll
    static void afterAll() {
        container.stop();
    }
}
