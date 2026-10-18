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
    void customParsersCanReplaceNullArgumentsAndReturns() {
        var replacement = new Box<>("created-from-null");
        var parser = new ReplacingParser(replacement);
        var target = new TestService(null);
        AnnotationParser.addTypeParser(parser);
        try {
            assertSame(replacement, proxy(target).process(null));
            assertSame(replacement, target.received);
            assertEquals(2, parser.calls);
        } finally {
            AnnotationParser.removeTypeParser(parser);
        }
    }

    @Test
    void nullParserResultsPropagateThroughArgumentsAndReturns() {
        var parser = new ReplacingParser(null);
        var original = new Box<>("123456@qq.com");
        var target = new TestService(original);
        AnnotationParser.addTypeParser(parser);
        try {
            assertNull(proxy(target).process(original));
            assertNull(target.received);
            assertEquals(2, parser.calls);
        } finally {
            AnnotationParser.removeTypeParser(parser);
        }
    }

    private static TestService proxy(TestService target) {
        var factory = new ProxyFactory(target);
        factory.addAdvice(new BlurMethodInterceptor());
        return (TestService) factory.getProxy();
    }

    /**
     * A custom wrapper handled by an application-provided type parser.
     */
    public record Box<T>(T value) {
    }

    /**
     * Exercises annotated parameters and returns in the same proxied invocation.
     */
    public static class TestService {

        private final Box<String> result;
        private Box<String> received = new Box<>("not-invoked");

        public TestService(Box<String> result) {
            this.result = result;
        }

        public Box<@Email String> process(Box<@Email String> value) {
            received = value;
            return result;
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
