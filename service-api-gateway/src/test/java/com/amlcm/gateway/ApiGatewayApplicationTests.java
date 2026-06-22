package com.amlcm.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * Smoke test for the API Gateway. Verifies the reactive Spring context
 * loads cleanly without depending on Config Server or Eureka being up.
 */
@SpringBootTest
@TestPropertySource(properties = {
    "spring.cloud.config.enabled=false",
    "eureka.client.enabled=false",
    "spring.cloud.discovery.enabled=false",
    "server.port=0"
})
class ApiGatewayApplicationTests {

    @Test
    void contextLoads() {
        // Context startup is the assertion.
    }
}
