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
            return new CustomizedResponse<>(parsed, response.getMessage(), response.getCode());
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
