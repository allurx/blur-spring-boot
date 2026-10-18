/*
 * Copyright 2024 allurx
 * SPDX-License-Identifier: Apache-2.0
 */

package io.allurx.blur.spring.boot.sample.web.config;

import io.allurx.annotation.parser.AnnotationParser;
import io.allurx.annotation.parser.type.TypeParser;
import io.allurx.blur.spring.boot.autoconfigure.ResponseEntityTypeParser;
import io.allurx.blur.spring.boot.sample.web.model.CustomizedResponse;
import org.springframework.aop.framework.AopInfrastructureBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;

/**
 * Configuration class for data masking and obfuscation settings.
 * This class defines beans related to the customization of response parsing
 * to support data blur functionality.
 *
 * @author allurx
 */
@Configuration
public class BlurConfig {

    /**
     * Default constructor
     */
    public BlurConfig() {
    }

    /**
     * Registers the {@link CustomizedResponseTypeParser} as a bean in the Spring context.
     *
     * @return an instance of {@link CustomizedResponseTypeParser}
     */
    @Bean
    public TypeParser<CustomizedResponse<Object>, AnnotatedParameterizedType> typeParser() {
        return new CustomizedResponseTypeParser();
    }

    /**
     * Custom type parser for handling {@link CustomizedResponse} data types.
     * This parser processes the data within a customized response object
     * and applies the necessary annotations for data blurring.
     *
     * @see ResponseEntityTypeParser for standard response handling.
     */
    public static class CustomizedResponseTypeParser implements TypeParser<CustomizedResponse<Object>, AnnotatedParameterizedType>, AopInfrastructureBean {

        private final int order = AnnotationParser.randomOrder();

        /**
         * Default constructor
         */
        public CustomizedResponseTypeParser() {
        }

        @Override
        public CustomizedResponse<Object> parse(CustomizedResponse<Object> response, AnnotatedParameterizedType annotatedParameterizedType) {
            AnnotatedType typeArgument = annotatedParameterizedType.getAnnotatedActualTypeArguments()[0];
            Object parsed = AnnotationParser.parse(response.getData(), typeArgument);
            return new CustomizedResponse<>(parsed, response.getCode(), response.getMessage());
        }

        @Override
        public boolean support(Object value, AnnotatedType annotatedType) {
            return value instanceof CustomizedResponse && annotatedType instanceof AnnotatedParameterizedType;
        }

        @Override
        public int order() {
            return order;
        }
    }
}
