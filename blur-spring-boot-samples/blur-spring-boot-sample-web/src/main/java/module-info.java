/*
 * Copyright 2024 allurx
 * SPDX-License-Identifier: Apache-2.0
 */
/**
 * blur spring boot sample web module
 *
 * @author allurx
 */
module io.allurx.blur.spring.boot.sample.web {
    requires spring.aop;
    requires spring.beans;
    requires spring.web;
    requires spring.context;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires io.allurx.blur;
    requires io.allurx.annotation.parser;
    requires io.allurx.blur.spring.boot.autoconfigure;
    exports io.allurx.blur.spring.boot.sample.web;
    exports io.allurx.blur.spring.boot.sample.web.config;
    exports io.allurx.blur.spring.boot.sample.web.controller;
    exports io.allurx.blur.spring.boot.sample.web.model;
    opens io.allurx.blur.spring.boot.sample.web;
    opens io.allurx.blur.spring.boot.sample.web.config;
    opens io.allurx.blur.spring.boot.sample.web.controller;
    opens io.allurx.blur.spring.boot.sample.web.model;
}