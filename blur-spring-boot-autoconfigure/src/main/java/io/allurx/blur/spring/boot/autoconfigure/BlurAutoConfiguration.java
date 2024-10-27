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

package io.allurx.blur.spring.boot.autoconfigure;

import io.allurx.annotation.parser.type.TypeParser;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.lang.reflect.AnnotatedParameterizedType;
import java.util.Optional;

/**
 * Autoconfiguration class for enabling blur functionality, which includes data masking,
 * obfuscating, and anonymizing capabilities. Configures default beans if not already defined.
 * <p>
 * This configuration class loads properties from {@link BlurProperties} and sets up
 * aspect-oriented data handling for specified pointcuts.
 * </p>
 *
 * @author allurx
 */
@AutoConfiguration
@EnableConfigurationProperties(BlurProperties.class)
public class BlurAutoConfiguration {

    private static final String BLUR_ADVISOR = "blurAdvisor";
    private final BlurProperties blurProperties;
    static final ThreadLocal<SpringApplication> SPRING_APPLICATION_HOLDER = new ThreadLocal<>();

    /**
     * Constructor for {@link BlurAutoConfiguration}.
     *
     * @param blurProperties properties configuration for blur functionality
     */
    public BlurAutoConfiguration(BlurProperties blurProperties) {
        this.blurProperties = blurProperties;
    }

    /**
     * Defines a bean for the blur advisor, which applies data masking and obfuscation
     * advice to methods matched by the pointcut expression.
     *
     * @return a configured {@link Advisor} with pointcut and advice set up
     */
    @Bean
    @ConditionalOnMissingBean(name = BLUR_ADVISOR)
    public Advisor blurAdvisor() {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setAdvice(new BlurMethodInterceptor());
        advisor.setExpression(pointcutExpression());
        return advisor;
    }

    /**
     * Registers a type parser bean for {@link ResponseEntity} to handle annotated type
     * resolution during runtime, enabling enhanced blur functionality.
     *
     * @return a {@link TypeParser} implementation for {@link ResponseEntity} types
     */
    @Bean
    public TypeParser<ResponseEntity<Object>, AnnotatedParameterizedType> responseEntityResolver() {
        return new ResponseEntityTypeParser();
    }

    /**
     * Retrieves the pointcut expression used for data blur application. If a custom
     * expression is not configured, defaults to targeting all methods in the main application package.
     *
     * @return the pointcut expression string for data blur operations
     */
    private String pointcutExpression() {
        SpringApplication springApplication = SPRING_APPLICATION_HOLDER.get();
        Assert.notNull(springApplication, () -> "Failed to retrieve SpringApplication. The current project may not be a Spring Boot application.");
        return Optional.ofNullable(blurProperties.getPointcutExpression())
                .orElse("execution(* " + springApplication.getMainApplicationClass().getPackage().getName() + "..*.*(..))");
    }
}
