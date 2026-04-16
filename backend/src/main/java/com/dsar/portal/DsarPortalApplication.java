package com.dsar.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the DSAR Portal application.
 * Starts an embedded Tomcat server on port 8080.
 * Demo users are seeded automatically via DataSeedService on startup.
 */
@SpringBootApplication
public class DsarPortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(DsarPortalApplication.class, args);
    }
}
