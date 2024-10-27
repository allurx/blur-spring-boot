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