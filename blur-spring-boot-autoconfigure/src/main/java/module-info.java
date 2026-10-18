/*
 * Copyright 2024 allurx
 * SPDX-License-Identifier: Apache-2.0
 */
/**
 * blur spring boot autoconfigure module
 *
 * @author allurx
 */
module io.allurx.blur.spring.boot.autoconfigure {
    requires org.aspectj.weaver;
    requires spring.aop;
    requires spring.core;
    requires spring.web;
    requires spring.context;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires io.allurx.blur;
    requires io.allurx.kit.base;
    requires io.allurx.annotation.parser;
    exports io.allurx.blur.spring.boot.autoconfigure;
}