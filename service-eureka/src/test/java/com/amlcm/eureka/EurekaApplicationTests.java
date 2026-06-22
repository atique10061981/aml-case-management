package com.amlcm.eureka;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * Smoke test for the Eureka server.
 *
 * <p>Disables the Config Server client and Eureka's own self-registration
 * so the test runs standalone — no external services need to be up.
 *
 * <p>Note: we deliberately do NOT override {@code spring.config.import}.
 * The application.yml entry {@code optional:configserver:http://localhost:8888}
 * already handles the missing-Config-Server case gracefully thanks to its
 * {@code optional:} prefix. Combined with {@code spring.cloud.config.enabled=false}
 * below, the {@code configserver:} scheme becomes an inert no-op during tests.
 */
@SpringBootTest
@TestPropertySource(properties = {
    // Disable the Spring Cloud Config client. With this off, the
    // `configserver:` scheme handler isn't registered; the optional:
    // prefix in application.yml then silently skips the import.
    "spring.cloud.config.enabled=false",

    // Don't try to self-register or fetch the registry during the test —
    // we ARE the server, registering with ourselves would loop forever.
    "eureka.client.register-with-eureka=false",
    "eureka.client.fetch-registry=false",

    // Random port so concurrent test runs don't collide on 8761
    "server.port=0"
})
class EurekaApplicationTests {

    @Test
    void contextLoads() {
        // Context startup is the assertion. If the @SpringBootApplication
        // wiring is broken, the test fails before this method runs.
    }
}
