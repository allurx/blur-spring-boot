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
