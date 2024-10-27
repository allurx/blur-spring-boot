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

package io.allurx.blur.spring.boot.autoconfigure;

import org.aspectj.weaver.tools.PointcutPrimitive;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for data obfuscation. The {@link #pointcutExpression} property
 * supports the following AspectJ pointcut primitives:
 * <ul>
 *     <li>{@link PointcutPrimitive#EXECUTION}</li>
 *     <li>{@link PointcutPrimitive#ARGS}</li>
 *     <li>{@link PointcutPrimitive#REFERENCE}</li>
 *     <li>{@link PointcutPrimitive#THIS}</li>
 *     <li>{@link PointcutPrimitive#TARGET}</li>
 *     <li>{@link PointcutPrimitive#WITHIN}</li>
 *     <li>{@link PointcutPrimitive#AT_ANNOTATION}</li>
 *     <li>{@link PointcutPrimitive#AT_WITHIN}</li>
 *     <li>{@link PointcutPrimitive#AT_ARGS}</li>
 *     <li>{@link PointcutPrimitive#AT_TARGET}</li>
 * </ul>
 * <p>
 * Configured with the prefix "blur" for binding in Spring Boot applications.
 * </p>
 *
 * @author allurx
 */
@ConfigurationProperties(prefix = "blur")
public class BlurProperties {

    /**
     * Default constructor
     */
    public BlurProperties() {
    }

    /**
     * Pointcut expression defining the target methods for data blur functionality.
     * Defaults to all methods within the package of the Spring Boot main application class and its sub-packages.
     */
    private String pointcutExpression;

    /**
     * Retrieves the pointcut expression used for AOP.
     *
     * @return the pointcut expression as a String.
     */
    public String getPointcutExpression() {
        return pointcutExpression;
    }

    /**
     * Sets the pointcut expression for AOP.
     *
     * @param pointcutExpression the pointcut expression to set.
     */
    public void setPointcutExpression(String pointcutExpression) {
        this.pointcutExpression = pointcutExpression;
    }

}

