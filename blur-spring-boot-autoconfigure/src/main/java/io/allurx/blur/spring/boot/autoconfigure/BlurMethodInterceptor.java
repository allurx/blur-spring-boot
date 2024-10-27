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

import io.allurx.annotation.parser.handler.Parse;
import io.allurx.annotation.parser.type.Cascade;
import io.allurx.blur.Blur;
import io.allurx.kit.base.reflection.AnnotatedTypeToken;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.AnnotatedTypeVariable;
import java.lang.reflect.AnnotatedWildcardType;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Interceptor for applying data blur functionality on method arguments and return values.
 * This interceptor applies masking, obfuscation, and anonymization based on annotation-driven rules.
 * <p>
 * It dynamically checks for blur-related annotations on method parameters and return types,
 * ensuring only annotated data is processed, which optimizes performance by avoiding unnecessary processing.
 * </p>
 *
 * @author allurx
 * @see Blur
 * @see MethodInterceptor
 * @see AnnotatedTypeToken
 * @see Parse
 * @see Cascade
 */
public class BlurMethodInterceptor implements MethodInterceptor {


    /**
     * Default constructor
     */
    public BlurMethodInterceptor() {
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Object[] arguments = invocation.getArguments();
        Parameter[] parameters = method.getParameters();

        IntStream.range(0, parameters.length)
                .filter(i -> requiresBlur(parameters[i].getAnnotatedType()))
                .forEach(i -> arguments[i] = Blur.blur(arguments[i], AnnotatedTypeToken.of(parameters[i].getAnnotatedType())));

        Object proceed = invocation.proceed();
        AnnotatedType returnType = method.getAnnotatedReturnType();

        return requiresBlur(returnType) ? Blur.blur(proceed, AnnotatedTypeToken.of(returnType)) : proceed;
    }

    /**
     * Determines if an object, identified by its {@link AnnotatedType}, requires blurring.
     * Objects marked with blur-related annotations are processed, while others are skipped
     * to maintain performance by avoiding redundant processing or object creation.
     * <p>
     * Although using a dedicated {@code @Blur} annotation might simplify identification,
     * this method avoids extra annotations by dynamically analyzing the presence of blur-triggering annotations.
     * </p>
     *
     * @param annotatedType the {@link AnnotatedType} of the object to evaluate
     * @return {@code true} if the object requires blurring, {@code false} otherwise
     */
    private boolean requiresBlur(AnnotatedType annotatedType) {
        return Arrays.stream(annotatedType.getDeclaredAnnotations()).anyMatch(annotation -> annotation.annotationType().isAnnotationPresent(Parse.class)) ||
                annotatedType.getDeclaredAnnotation(Cascade.class) != null ||
                switch (annotatedType) {
                    case AnnotatedTypeVariable annotatedTypeVariable ->
                            Arrays.stream(annotatedTypeVariable.getAnnotatedBounds()).anyMatch(this::requiresBlur);
                    case AnnotatedWildcardType annotatedWildcardType ->
                            Stream.of(annotatedWildcardType.getAnnotatedUpperBounds(), annotatedWildcardType.getAnnotatedLowerBounds())
                                    .flatMap(Arrays::stream)
                                    .anyMatch(this::requiresBlur);
                    case AnnotatedParameterizedType annotatedParameterizedType ->
                            Arrays.stream(annotatedParameterizedType.getAnnotatedActualTypeArguments())
                                    .anyMatch(this::requiresBlur);
                    case AnnotatedArrayType annotatedArrayType ->
                            requiresBlur(annotatedArrayType.getAnnotatedGenericComponentType());
                    default -> false;
                };
    }

}
