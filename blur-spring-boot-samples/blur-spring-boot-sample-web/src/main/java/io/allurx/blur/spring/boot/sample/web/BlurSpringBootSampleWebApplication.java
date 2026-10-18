/*
 * Copyright 2024 allurx
 * SPDX-License-Identifier: Apache-2.0
 */

package io.allurx.blur.spring.boot.sample.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the Blur Spring Boot sample web application.
 * This class initializes and starts the Spring Boot application.
 *
 * @author allurx
 */
@SpringBootApplication
public class BlurSpringBootSampleWebApplication {

    /**
     * Default constructor
     */
    public BlurSpringBootSampleWebApplication() {
    }

    /**
     * Main method to run the Blur Spring Boot application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(BlurSpringBootSampleWebApplication.class, args);
    }
}
