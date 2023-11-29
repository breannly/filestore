package com.filestore.filestore;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"spring.flyway.enabled=false"})
class FilestoreApplicationTests {

    @Test
    void contextLoads() {
    }
}
