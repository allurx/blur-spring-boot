/*
 * Copyright 2024 allurx
 * SPDX-License-Identifier: Apache-2.0
 */

package io.allurx.blur.spring.boot.sample.web.model;

import io.allurx.blur.annotation.Email;
import io.allurx.blur.annotation.PhoneNumber;

import java.util.StringJoiner;

/**
 * Represents a person with a phone number and email address.
 *
 * @author allurx
 */
public class Person {

    @PhoneNumber
    private String phoneNumber;

    @Email
    private String email;

    /**
     * Default constructor for creating an empty Person instance.
     */
    public Person() {
    }

    /**
     * Constructs a Person with the specified phone number and email.
     *
     * @param phoneNumber the person's phone number
     * @param email       the person's email address
     */
    public Person(String phoneNumber, String email) {
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    /**
     * Returns the phone number of this person.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of this person.
     *
     * @param phoneNumber the phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the email address of this person.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of this person.
     *
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Person.class.getSimpleName() + "[", "]")
                .add("phoneNumber='" + phoneNumber + "'")
                .add("email='" + email + "'")
                .toString();
    }
}
