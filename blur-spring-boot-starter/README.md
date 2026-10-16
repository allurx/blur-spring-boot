# Blur Spring Boot starter

This starter collects the dependencies needed to enable annotation-based data masking in a Spring Boot application:

- `blur-spring-boot-autoconfigure` supplies the Blur advisor, configuration properties, and `ResponseEntity` parser.
- `spring-boot-starter-aspectj` enables Spring Boot's AspectJ-based proxy support.

The starter declares no Java API. Its implementation is the dependency declaration in [pom.xml](https://github.com/allurx/blur-spring-boot/blob/main/blur-spring-boot-starter/pom.xml). The main, sources, and Javadoc artifacts contain this guide and the Apache 2.0 license; Java implementation sources and API documentation belong to `blur-spring-boot-autoconfigure`.

For supported versions, configuration, customization, and complete examples, see the [project guide](https://github.com/allurx/blur-spring-boot#usage). Consumers can configure `blur.pointcut-expression`, supply a `blurAdvisor`, or register a custom `TypeParser` bean as described there.
