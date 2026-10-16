# blur-spring-boot

This project integrates the [blur](https://github.com/allurx/blur) library into Spring Boot to enable automatic data blurring. Using Spring AOP, this library globally intercepts targeted methods to apply blurring (data masking) by default on all methods within the package of the Spring Boot application's main class and its sub-packages.

To further customize the blurring, you can specify a custom pointcut expression using `blur`-prefixed parameters in the Spring configuration file. Alternatively, you can define an `Advisor` named `blurAdvisor` in the Spring context to gain more granular control over the blurring process.

# Usage

## Requirements

Use JDK 25 or later and Spring Boot 4.1. The managed Blur and Spring Boot versions are declared in [pom.xml](pom.xml).

Version 4.0.0 is not yet published. These requirements describe the current checkout; applications consuming a published 3.x artifact should use the corresponding 3.x documentation and stack.

## Maven Dependency

```xml
<dependency>
    <groupId>io.allurx</groupId>
    <artifactId>blur-spring-boot-starter</artifactId>
    <version>${latest version}</version>
</dependency>
```

## Custom response types

The interceptor processes annotated arguments and return values. It includes a `TypeParser` for the body of Spring's `ResponseEntity`. For a custom response wrapper, register a `TypeParser` bean that parses the payload and preserves the wrapper's other fields.

The working example uses [CustomizedResponse](blur-spring-boot-samples/blur-spring-boot-sample-web/src/main/java/io/allurx/blur/spring/boot/sample/web/model/CustomizedResponse.java) and its [type parser bean](blur-spring-boot-samples/blur-spring-boot-sample-web/src/main/java/io/allurx/blur/spring/boot/sample/web/config/BlurConfig.java). The parser is an `AopInfrastructureBean` so that it is not intercepted by the advisor that uses it.

## Examples

### Blurring `ResponseEntity` Type Data
1. [Blurred method example](blur-spring-boot-samples/blur-spring-boot-sample-web/src/main/java/io/allurx/blur/spring/boot/sample/web/controller/ResponseEntityBlurController.java)
2. [Related test cases](blur-spring-boot-samples/blur-spring-boot-sample-web/src/test/java/io/allurx/blur/spring/boot/sample/web/test/ResponseEntityBlurTest.java)

### Blurring `CustomizedResponse` Type Data
1. [Blurred method example](blur-spring-boot-samples/blur-spring-boot-sample-web/src/main/java/io/allurx/blur/spring/boot/sample/web/controller/CustomizedResponseBlurController.java)
2. [Related test cases](blur-spring-boot-samples/blur-spring-boot-sample-web/src/test/java/io/allurx/blur/spring/boot/sample/web/test/CustomizedResponseBlurTest.java)

# Upgrading from 3.x

The unreleased 4.0.0 upgrade raises the Java baseline from 21 to 25 and moves from Spring Boot 3 to Spring Boot 4.1, Spring Framework 7, and Jackson 3. Applications that need the earlier stack should remain on the 3.x release line. Review the [Spring Boot migration guide](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Migration-Guide) when upgrading the application.

The starter uses Blur 6 and its current [masking rules](https://github.com/allurx/blur#notes), including Unicode code-point offsets and the object traversal rules supplied by annotation-parser. Existing `blur.pointcut-expression`, `blurAdvisor`, and `TypeParser` extension points remain available. JSON requests and responses in the sample now use Spring Boot's Jackson 3 integration.

# Development

Use JDK 25 and Maven 3.9.x. From the repository root, run:

```shell
mvn -B -ntp clean verify
```

This runs the local HTTP regression tests without signing or publishing artifacts. To also verify sources and Javadoc, run `mvn -B -ntp -Prelease "-Dgpg.skip=true" clean verify`. CI uses the [shared allurx-build workflows](https://github.com/allurx/allurx-build) and [dependency-update policy](https://github.com/allurx/allurx-build/blob/main/docs/dependency-updates.md). Renovate is configured to open updates against `dev`; eligible routine updates merge after verification succeeds. Integration from `dev` into `main` remains a separate step.

## Releases

Formal `vX.Y.Z` tags use the [shared release workflow](https://github.com/allurx/allurx-build/blob/main/docs/release-guide.md). Before publishing, configure the `maven-central` Environment, namespace-authorized Central credentials, signing key, and tag protections described in that guide. Adding the workflow does not publish this checkout.

The complete reactor, including the HTTP sample, is built and tested. Only the root POM, autoconfigure library, and dependency-only starter are published; the sample modules use Central's `skipPublishing` property. The starter's source and Javadoc companions contain its [dependency and configuration guide](blur-spring-boot-starter/README.md) and license because it has no Java implementation.

# License
[Apache License 2.0](LICENSE.txt)
