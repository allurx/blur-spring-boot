# blur-spring-boot

This project integrates the [blur](https://github.com/allurx/blur) library into Spring Boot to enable automatic data blurring. Using Spring AOP, this library globally intercepts targeted methods to apply blurring (data masking) by default on all methods within the package of the Spring Boot application's main class and its sub-packages.

To further customize the blurring, you can specify a custom pointcut expression using `blur`-prefixed parameters in the Spring configuration file. Alternatively, you can define an `Advisor` named `blurAdvisor` in the Spring context to gain more granular control over the blurring process.

# Usage

## Spring Boot Version

3.3.5

## Maven Dependency

```xml
<dependency>
    <groupId>io.allurx</groupId>
    <artifactId>blur-spring-boot-starter</artifactId>
    <version>${latest version}</version>
</dependency>
```

## Notes
By default, this library enables blurring for methods returning Spring’s `ResponseEntity` type only. If your application uses a custom response entity, such as:
```java
public class CustomizedResponse<T> {

    public T data;

    public String code;

    public String message;

    public CustomizedResponse() {}

    public CustomizedResponse(T data, String code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

}
```
then you’ll need to configure a type parser to handle custom types for blurring.
```java
@Configuration
public class BlurConfig {

    /**
     * Registers the {@code CustomizedResponseTypeParser} as a bean in the Spring context.
     *
     * @return {@link CustomizedResponseTypeParser}
     */
    @Bean
    public TypeParser<CustomizedResponse<Object>, AnnotatedParameterizedType> typeParser() {
        return new CustomizedResponseTypeParser();
    }

    /**
     * Custom type parser to handle {@code CustomizedResponse} type data.
     */
    public static class CustomizedResponseTypeParser
            implements TypeParser<CustomizedResponse<Object>, AnnotatedParameterizedType>, AopInfrastructureBean {

        private final int order = AnnotationParser.randomOrder();

        @Override
        public CustomizedResponse<Object> parse(CustomizedResponse<Object> response, AnnotatedParameterizedType type) {
            AnnotatedType typeArgument = type.getAnnotatedActualTypeArguments()[0];
            Object blurredData = AnnotationParser.parse(response.getData(), typeArgument);
            return new CustomizedResponse<>(blurredData, response.getMessage(), response.getCode());
        }

        @Override
        public boolean support(Object value, AnnotatedType annotatedType) {
            return value instanceof CustomizedResponse && annotatedType instanceof AnnotatedParameterizedType;
        }

        @Override
        public int order() {
            return order;
        }
    }
}
```
This configuration blurs `CustomizedResponse` type objects, 
typically applying blurring only to the actual data (`data`) within the response body. 
After adding this type parser to the Spring context, 
you need only annotate the generic parameter of the return object in the method with the blurring annotation to enable automatic blurring for `CustomizedResponse` type data.

## Examples

### Blurring `ResponseEntity` Type Data
1. [Blurred method example](blur-spring-boot-samples/blur-spring-boot-sample-web/src/main/java/io/allurx/blur/spring/boot/sample/web/controller/ResponseEntityBlurController.java)
2. [Related test cases](blur-spring-boot-samples/blur-spring-boot-sample-web/src/test/java/io/allurx/blur/spring/boot/sample/web/test/ResponseEntityBlurTest.java)

### Blurring `CustomizedResponse` Type Data
1. [Blurred method example](blur-spring-boot-samples/blur-spring-boot-sample-web/src/main/java/io/allurx/blur/spring/boot/sample/web/controller/CustomizedResponseBlurController.java)
2. [Related test cases](blur-spring-boot-samples/blur-spring-boot-sample-web/src/test/java/io/allurx/blur/spring/boot/sample/web/test/CustomizedResponseBlurTest.java)

# License
[Apache License 2.0](LICENSE.txt)
