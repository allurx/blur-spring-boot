/*
 * Copyright 2024 allurx
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
