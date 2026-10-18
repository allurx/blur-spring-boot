# blur-spring-boot

[English](README.md) | 简体中文

本项目将 [blur](https://github.com/allurx/blur) 集成到 Spring Boot，通过 Spring AOP 拦截目标方法，自动执行数据脱敏。默认切入范围为 Spring Boot 主应用类所在包及其子包中的方法。

可在 Spring 配置文件中使用以 `blur` 为前缀的配置项，自定义切点表达式；也可以在 Spring 容器中定义名为 `blurAdvisor` 的 `Advisor`，更细致地控制脱敏行为。

# 使用

## 环境要求

使用 JDK 25 或更高版本及 Spring Boot 4.1。受管理的 Blur 和 Spring Boot 版本见 [pom.xml](pom.xml)。

这些要求适用于 4.x 发行系列。使用 3.x 制品的应用应参考对应的 3.x 文档和技术栈。

## Maven 依赖

将 `LATEST_VERSION` 替换为 [Maven Central](https://central.sonatype.com/artifact/io.allurx/blur-spring-boot-starter) 上已发布的 4.x 版本。

```xml
<dependency>
    <groupId>io.allurx</groupId>
    <artifactId>blur-spring-boot-starter</artifactId>
    <version>LATEST_VERSION</version>
</dependency>
```

## 自定义响应类型

拦截器处理带注解的参数和返回值，内置 `TypeParser` 用于解析 Spring `ResponseEntity` 的响应体。对于自定义响应包装类型，可注册 `TypeParser` bean，解析其中的数据并保留包装对象的其他字段。

可运行示例使用 [CustomizedResponse](blur-spring-boot-samples/blur-spring-boot-sample-web/src/main/java/io/allurx/blur/spring/boot/sample/web/model/CustomizedResponse.java) 及其[类型解析器 bean](blur-spring-boot-samples/blur-spring-boot-sample-web/src/main/java/io/allurx/blur/spring/boot/sample/web/config/BlurConfig.java)。解析器实现 `AopInfrastructureBean`，避免被使用它的 advisor 拦截。

## 示例

### `ResponseEntity` 类型数据脱敏

1. [方法脱敏示例](blur-spring-boot-samples/blur-spring-boot-sample-web/src/main/java/io/allurx/blur/spring/boot/sample/web/controller/ResponseEntityBlurController.java)
2. [相关测试](blur-spring-boot-samples/blur-spring-boot-sample-web/src/test/java/io/allurx/blur/spring/boot/sample/web/test/ResponseEntityBlurTest.java)

### `CustomizedResponse` 类型数据脱敏

1. [方法脱敏示例](blur-spring-boot-samples/blur-spring-boot-sample-web/src/main/java/io/allurx/blur/spring/boot/sample/web/controller/CustomizedResponseBlurController.java)
2. [相关测试](blur-spring-boot-samples/blur-spring-boot-sample-web/src/test/java/io/allurx/blur/spring/boot/sample/web/test/CustomizedResponseBlurTest.java)

# 从 3.x 升级

4.0.0 将 Java 基线从 21 提高到 25，并从 Spring Boot 3 升级到 Spring Boot 4.1、Spring Framework 7 和 Jackson 3。仍需旧技术栈的应用应继续使用 3.x 发行系列。升级应用时，请参考 [Spring Boot 迁移指南](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Migration-Guide)。

starter 使用 Blur 7。脱敏偏移量按 Unicode 码点计算，而不是 UTF-16 代码单元。拦截器将 null 参数和返回值交给匹配的类型解析器处理；自定义解析器可以替换 null 输入，解析器返回 null 时会原样传播，不再恢复为原值。更新自定义注解和解析器时，请参考 [Blur 7 迁移说明](https://github.com/allurx/blur#upgrading-from-6x)。现有 `blur.pointcut-expression`、`blurAdvisor` 和 `TypeParser` 扩展入口仍然可用。示例中的 JSON 请求与响应现在使用 Spring Boot 的 Jackson 3 集成。

# 开发

使用 JDK 25 和 Maven 3.9.x，在仓库根目录执行：

```shell
mvn -B -ntp clean verify
```

该命令运行本地 HTTP 回归测试，不签名或发布制品。如需同时验证源码包和 Javadoc，运行 `mvn -B -ntp -Prelease "-Dgpg.skip=true" clean verify`。CI 使用 [allurx-build 共享工作流](https://github.com/allurx/allurx-build)和[依赖更新策略](https://github.com/allurx/allurx-build/blob/main/docs/dependency-updates.md)。Renovate 向 `dev` 提出更新 PR；符合策略的常规更新在验证通过后合并。将 `dev` 集成到 `main` 仍是独立步骤。

## 发布

正式的 `vX.Y.Z` tag 使用[共享发布工作流](https://github.com/allurx/allurx-build/blob/main/docs/release-guide.md)。发布前，按该指南配置 `maven-central` Environment、具有命名空间权限的 Central 凭据、签名密钥及 tag 保护。添加工作流本身不会发布当前检出的源码。

构建与测试覆盖包含 HTTP 示例在内的完整 reactor。仅发布根 POM、自动配置库和仅声明依赖的 starter；示例模块通过 Central 的 `skipPublishing` 属性排除。starter 没有 Java 实现，其源码和 Javadoc 附件包含[依赖与配置指南](blur-spring-boot-starter/README.md)及许可证。

# 许可证

[Apache License 2.0](LICENSE.txt)
