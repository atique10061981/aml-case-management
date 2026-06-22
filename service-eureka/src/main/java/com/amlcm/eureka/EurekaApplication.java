package com.amlcm.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka Service Registry — the white pages for every microservice
 * in the AML Case Management platform.
 *
 * <p>Each domain service registers itself here on startup and sends
 * heartbeats every 30 seconds. Callers (the API Gateway, inter-service
 * Feign clients) ask Eureka "where is service-X?" and get back a list
 * of healthy instances to load-balance across.
 *
 * <h2>Endpoints</h2>
 * <ul>
 *   <li>{@code GET /} — HTML dashboard</li>
 *   <li>{@code GET /eureka/apps} — XML/JSON list of registered applications</li>
 *   <li>{@code GET /eureka/apps/{appName}} — instances of one app</li>
 *   <li>{@code POST /eureka/apps/{appName}} — register (called by clients, not humans)</li>
 * </ul>
 *
 * <p>This server runs in single-node mode (it doesn't replicate to
 * peer Eurekas). In production we'd run 2–3 instances with replication
 * for high availability.
 *
 * @see <a href="https://docs.spring.io/spring-cloud-netflix/reference/spring-cloud-netflix.html">Spring Cloud Netflix reference</a>
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }
}
