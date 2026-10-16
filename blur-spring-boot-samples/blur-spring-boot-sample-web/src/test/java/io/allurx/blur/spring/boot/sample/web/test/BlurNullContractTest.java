/*
 * Copyright 2026 allurx
 * SPDX-License-Identifier: Apache-2.0
 */
package io.allurx.blur.spring.boot.sample.web.test;

import io.allurx.annotation.parser.AnnotationParser;
import io.allurx.annotation.parser.type.TypeParser;
import io.allurx.blur.annotation.Email;
import io.allurx.blur.spring.boot.autoconfigure.BlurMethodInterceptor;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.ParameterizedType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Verifies the released Blur API's null contract through the public AOP interceptor.
 *
 * @author allurx
 */
class BlurNullContractTest {

    @Test
    void nullArgumentsAndReturnsDoNotInvokeCustomParsers() {
        var parser = new ReplacingParser(new Box<>("created-from-null"));
        AnnotationParser.addTypeParser(parser);
        try {
            assertNull(proxy().echo(null));
            assertEquals(0, parser.calls);
        } finally {
            AnnotationParser.removeTypeParser(parser);
        }
    }

    @Test
    void nullParserResultsPreserveArgumentsAndReturns() {
        var parser = new ReplacingParser(null);
        var original = new Box<>("123456@qq.com");
        AnnotationParser.addTypeParser(parser);
        try {
            assertSame(original, proxy().echo(original));
            assertEquals(2, parser.calls);
        } finally {
            AnnotationParser.removeTypeParser(parser);
        }
    }

    private static EchoService proxy() {
        var factory = new ProxyFactory(new EchoService());
        factory.addAdvice(new BlurMethodInterceptor());
        return (EchoService) factory.getProxy();
    }

    /**
     * A custom wrapper handled by an application-provided type parser.
     */
    public record Box<T>(T value) {
    }

    /**
     * Exercises annotated parameters and returns in the same proxied invocation.
     */
    public static class EchoService {

        public Box<@Email String> echo(Box<@Email String> value) {
            return value;
        }
    }

    private static final class ReplacingParser implements TypeParser<Object, AnnotatedType> {

        private final int order = AnnotationParser.randomOrder();
        private final Object replacement;
        private int calls;

        private ReplacingParser(Object replacement) {
            this.replacement = replacement;
        }

        @Override
        public Object parse(Object input, AnnotatedType type) {
            calls++;
            return replacement;
        }

        @Override
        public boolean support(Object input, AnnotatedType type) {
            return type.getType() instanceof ParameterizedType parameterized
                    && parameterized.getRawType() == Box.class;
        }

        @Override
        public int order() {
            return order;
        }
    }
}
