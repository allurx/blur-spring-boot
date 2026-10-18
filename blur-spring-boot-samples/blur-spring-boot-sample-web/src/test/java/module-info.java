/*
 * Copyright 2024 allurx
 * SPDX-License-Identifier: Apache-2.0
 */
/**
 * blur spring boot sample web test module
 *
 * @author allurx
 */
module io.allurx.blur.spring.boot.sample.web.test {
    requires io.allurx.blur.spring.boot.sample.web;
    requires io.allurx.blur.spring.boot.autoconfigure;
    requires io.allurx.annotation.parser;
    requires io.allurx.blur;
    requires spring.aop;
    requires org.junit.jupiter.api;
    requires spring.beans;
    requires spring.boot.test;
    requires spring.boot.resttestclient;
    requires spring.core;
    requires spring.web;
    requires spring.boot;
    requires spring.test;
    opens io.allurx.blur.spring.boot.sample.web.test;
}