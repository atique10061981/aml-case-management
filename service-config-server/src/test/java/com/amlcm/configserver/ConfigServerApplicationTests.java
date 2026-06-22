package com.amlcm.configserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * Smoke test — verifies the Spring application context loads cleanly
 * with the Config Server auto-configuration in place.
 *
 * <p>This is the cheapest test in the pyramid: it doesn't assert any
 * business behaviour, but it catches all of the following at compile
 * + startup time:
 * <ul>
 *   <li>missing dependencies</li>
 *   <li>broken @Bean wiring</li>
 *   <li>invalid YAML in application.yml</li>
 *   <li>annotation misuse</li>
 * </ul>
 */
@SpringBootTest
@TestPropertySource(properties = {
    // Override the production search-locations during tests so we don't
    // depend on the host filesystem having a config-repo directory.
    "spring.cloud.config.server.native.search-locations=classpath:/test-config/"
})
class ConfigServerApplicationTests {

    @Test
    void contextLoads() {
        // The assertion is implicit: if the context fails to start,
        // the test fails. No body needed.
    }
}
