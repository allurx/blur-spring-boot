/*
 * Copyright 2024 allurx
 * SPDX-License-Identifier: Apache-2.0
 */

package io.allurx.blur.spring.boot.autoconfigure;

import io.allurx.annotation.parser.AnnotationParser;
import io.allurx.annotation.parser.type.TypeParser;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;

/**
 * Type parser for handling return values of type {@link ResponseEntity}.
 * This parser processes the body of the response entity based on its annotated parameterized type.
 *
 * @author allurx
 */
public class ResponseEntityTypeParser implements TypeParser<ResponseEntity<Object>, AnnotatedParameterizedType> {

    private final int order = AnnotationParser.randomOrder();

    /**
     * Default constructor
     */
    public ResponseEntityTypeParser() {
    }

    @Override
    public ResponseEntity<Object> parse(ResponseEntity<Object> responseEntity, AnnotatedParameterizedType annotatedParameterizedType) {
        AnnotatedType annotatedType = annotatedParameterizedType.getAnnotatedActualTypeArguments()[0];
        Object parsed = AnnotationParser.parse(responseEntity.getBody(), annotatedType);
        return new ResponseEntity<>(parsed, responseEntity.getHeaders(), responseEntity.getStatusCode());
    }

    @Override
    public boolean support(Object value, AnnotatedType annotatedType) {
        return value instanceof ResponseEntity && annotatedType instanceof AnnotatedParameterizedType;
    }

    @Override
    public int order() {
        return order;
    }
}
