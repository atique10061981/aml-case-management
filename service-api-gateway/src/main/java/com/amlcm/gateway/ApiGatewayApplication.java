package com.amlcm.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * API Gateway — the single network entry point for the AML Case Management
 * platform. Built on Spring Cloud Gateway (reactive, Netty-based).
 *
 * <h2>What it does today (Step 6)</h2>
 * <ul>
 *   <li>Routes by URL prefix to backend services (via Eureka discovery)</li>
 *   <li>Applies platform-wide default filters (CORS, dedup headers)</li>
 *   <li>Registers itself with Eureka so it shows up in the dashboard</li>
 *   <li>Exposes {@code /actuator/gateway/routes} for runtime inspection</li>
 * </ul>
 *
 * <h2>What it will do later</h2>
 * <ul>
 *   <li>JWT validation at the edge (Step 8 — needs Auth Service first)</li>
 *   <li>Redis-backed rate limiting (later — token-bucket per client/route)</li>
 *   <li>Aggregated Swagger UI (Step 18)</li>
 *   <li>Resilience4j circuit-breaker on outbound calls (Step 19)</li>
 * </ul>
 *
 * <h2>Why reactive</h2>
 * An edge service has to multiplex thousands of concurrent client connections
 * with limited threads. Spring MVC's "one thread per request" model doesn't
 * scale to that workload; reactive's async I/O does. Downstream domain
 * services (Step 10+) remain blocking — the reactive boundary stops here.
 *
 * @see <a href="https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway-server-webflux.html">Spring Cloud Gateway reference</a>
 */
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
