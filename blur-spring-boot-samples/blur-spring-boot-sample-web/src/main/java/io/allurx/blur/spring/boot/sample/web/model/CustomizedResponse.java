/*
 * Copyright 2024 allurx
 * SPDX-License-Identifier: Apache-2.0
 */

package io.allurx.blur.spring.boot.sample.web.model;

import java.util.StringJoiner;

/**
 * A generic response wrapper that holds the response data, status code, and message.
 *
 * @param <T> the type of the response data
 * @author allurx
 */
public class CustomizedResponse<T> {

    /**
     * The response data.
     */
    private T data;

    /**
     * The response code.
     */
    private String code;

    /**
     * The response message.
     */
    private String message;

    /**
     * Default constructor for creating an empty response.
     */
    public CustomizedResponse() {
    }

    /**
     * Constructs a new CustomizedResponse with the specified data, code, and message.
     *
     * @param data    the response data
     * @param code    the response code
     * @param message the response message
     */
    public CustomizedResponse(T data, String code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    /**
     * Creates a successful response with the specified data.
     *
     * @param <R>  the type of the response data
     * @param data the response data
     * @return a new CustomizedResponse with status code "200" and message "success"
     */
    public static <R> CustomizedResponse<R> ok(R data) {
        return new CustomizedResponse<>(data, "200", "success");
    }

    /**
     * Returns the response data.
     *
     * @return the response data
     */
    public T getData() {
        return data;
    }

    /**
     * Sets the response data.
     *
     * @param data the response data to set
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Returns the response code.
     *
     * @return the response code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the response code.
     *
     * @param code the response code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Returns the response message.
     *
     * @return the response message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the response message.
     *
     * @param message the response message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CustomizedResponse.class.getSimpleName() + "[", "]")
                .add("data=" + data)
                .add("code='" + code + "'")
                .add("message='" + message + "'")
                .toString();
    }

}
