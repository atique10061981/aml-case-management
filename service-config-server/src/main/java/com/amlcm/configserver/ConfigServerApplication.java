package com.amlcm.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Spring Cloud Config Server — central configuration store for every
 * microservice in the AML Case Management platform.
 *
 * <p>Services bootstrap by fetching their configuration from this
 * server's HTTP endpoints at startup. Configuration files live in
 * a backing store (native filesystem in dev, Git in production)
 * keyed by service name and Spring profile.
 *
 * <h2>Endpoint shape</h2>
 * <pre>
 *   GET /{service}/{profile}             → JSON of the resolved config
 *   GET /{service}-{profile}.yml         → raw YAML for the merged config
 *   GET /{service}-{profile}.properties  → same, as java.util.Properties
 * </pre>
 *
 * <p>Resolution order, highest precedence first:
 * <ol>
 *   <li>{@code {service}-{profile}.yml}</li>
 *   <li>{@code {service}.yml}</li>
 *   <li>{@code application-{profile}.yml}</li>
 *   <li>{@code application.yml}</li>
 * </ol>
 *
 * <p>This means a tiny override file like {@code service-customer-docker.yml}
 * can override one property while inheriting everything else from the
 * shared {@code application.yml}.
 *
 * @see <a href="https://docs.spring.io/spring-cloud-config/reference/server.html">Spring Cloud Config Server reference</a>
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
