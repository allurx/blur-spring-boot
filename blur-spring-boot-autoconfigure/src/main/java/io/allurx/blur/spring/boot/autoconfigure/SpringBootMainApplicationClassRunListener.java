/*
 * Copyright 2024 allurx
 * SPDX-License-Identifier: Apache-2.0
 */

package io.allurx.blur.spring.boot.autoconfigure;

import io.allurx.annotation.parser.AnnotationParser;
import io.allurx.annotation.parser.type.TypeParser;
import org.springframework.boot.bootstrap.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.Duration;

/**
 * Listener for Spring Boot application startup events.
 * This listener allows for actions to be taken during the startup phases of the application.
 * <p>
 * It specifically holds a reference to the main Spring application instance and manages its lifecycle,
 * allowing for necessary configuration to be applied during the startup process.
 * </p>
 *
 * @author allurx
 */
public class SpringBootMainApplicationClassRunListener implements SpringApplicationRunListener {

    private final String[] args;
    private final SpringApplication springApplication;

    /**
     * Constructs a new SpringBootMainApplicationClassRunListener.
     *
     * @param args              the command-line arguments passed to the Spring Boot application.
     * @param springApplication the SpringApplication instance to be managed by this listener.
     */
    public SpringBootMainApplicationClassRunListener(String[] args, SpringApplication springApplication) {
        this.args = args;
        this.springApplication = springApplication;
    }

    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        // Store the Spring application instance in a ThreadLocal for later use
        BlurAutoConfiguration.SPRING_APPLICATION_HOLDER.set(springApplication);
    }

    @Override
    public void ready(ConfigurableApplicationContext context, Duration timeTaken) {
        // Clean up the ThreadLocal after the application is ready
        BlurAutoConfiguration.SPRING_APPLICATION_HOLDER.remove();
        // Register all TypeParsers in the AnnotationParser for use during parsing
        for (TypeParser<?, ?> typeParser : context.getBeansOfType(TypeParser.class).values()) {
            AnnotationParser.addTypeParser(typeParser);
        }
    }
}
