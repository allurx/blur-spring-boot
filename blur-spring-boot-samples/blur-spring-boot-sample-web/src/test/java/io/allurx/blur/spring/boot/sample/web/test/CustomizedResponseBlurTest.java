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

package io.allurx.blur.spring.boot.sample.web.test;

import io.allurx.blur.spring.boot.sample.web.controller.CustomizedResponseBlurController;
import io.allurx.blur.spring.boot.sample.web.model.CustomizedResponse;
import io.allurx.blur.spring.boot.sample.web.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for the CustomizedResponseBlurController.
 * This class verifies the data blurring functionalities for various input types.
 *
 * @author allurx
 * @see CustomizedResponseBlurController
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomizedResponseBlurTest {

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Tests the blurring of a String parameter.
     */
    @Test
    void blurStringParameter() {
        var body = restTemplate.exchange(
                RequestEntity.get(new UriTemplate("/customizedResponseBlur/stringParameter?email={?}").expand("123456@qq.com")).build(),
                new ParameterizedTypeReference<CustomizedResponse<String>>() {
                }
        ).getBody();
        assertNotNull(body);
        assertEquals("1*****@qq.com", body.getData());
    }

    /**
     * Tests the blurring of a String return value.
     */
    @Test
    void blurStringReturnValue() {
        var body = restTemplate.exchange(
                RequestEntity.get(new UriTemplate("/customizedResponseBlur/stringReturnValue?email={?}").expand("123456@qq.com")).build(),
                new ParameterizedTypeReference<CustomizedResponse<String>>() {
                }
        ).getBody();
        assertNotNull(body);
        assertEquals("1*****@qq.com", body.getData());
    }

    /**
     * Tests the blurring of a Collection parameter.
     */
    @Test
    void blurCollectionParameter() throws URISyntaxException {
        var body = restTemplate.exchange(
                RequestEntity.post(new URI("/customizedResponseBlur/collectionParameter"))
                        .body(Stream.of("123456@qq.com", "123456@qq.com", "123456@qq.com").collect(Collectors.toList())),
                new ParameterizedTypeReference<CustomizedResponse<List<String>>>() {
                }
        ).getBody();
        assertNotNull(body);
        body.getData().forEach(s -> assertEquals("1*****@qq.com", s));
    }

    /**
     * Tests the blurring of a Collection return value.
     */
    @Test
    void blurCollectionReturnValue() throws URISyntaxException {
        var body = restTemplate.exchange(
                RequestEntity.post(new URI("/customizedResponseBlur/collectionReturnValue"))
                        .body(Stream.of("123456@qq.com", "123456@qq.com", "123456@qq.com").collect(Collectors.toList())),
                new ParameterizedTypeReference<CustomizedResponse<List<String>>>() {
                }
        ).getBody();
        assertNotNull(body);
        body.getData().forEach(s -> assertEquals("1*****@qq.com", s));
    }

    /**
     * Tests the blurring of a Map parameter.
     */
    @Test
    void blurMapParameter() throws URISyntaxException {
        var map = Stream.of("allurx").collect(Collectors.toMap(s -> s, o -> new Person("12345678910", "123456@qq.com")));
        var body = restTemplate.exchange(
                RequestEntity.post(new URI("/customizedResponseBlur/mapParameter")).body(map),
                new ParameterizedTypeReference<CustomizedResponse<Map<String, Person>>>() {
                }
        ).getBody();
        assertNotNull(body);
        body.getData().forEach((s, person) -> {
            assertEquals("a*****", s);
            assertEquals("123****8910", person.getPhoneNumber());
            assertEquals("1*****@qq.com", person.getEmail());
        });
    }

    /**
     * Tests the blurring of a Map return value.
     */
    @Test
    void blurMapReturnValue() throws URISyntaxException {
        var map = Stream.of("allurx").collect(Collectors.toMap(s -> s, o -> new Person("12345678910", "123456@qq.com")));
        var body = restTemplate.exchange(
                RequestEntity.post(new URI("/customizedResponseBlur/mapReturnValue")).body(map),
                new ParameterizedTypeReference<CustomizedResponse<Map<String, Person>>>() {
                }
        ).getBody();
        assertNotNull(body);
        body.getData().forEach((s, person) -> {
            assertEquals("a*****", s);
            assertEquals("123****8910", person.getPhoneNumber());
            assertEquals("1*****@qq.com", person.getEmail());
        });
    }

    /**
     * Tests the blurring of an Array parameter.
     */
    @Test
    void blurArrayParameter() throws URISyntaxException {
        var body = restTemplate.exchange(
                RequestEntity.post(new URI("/customizedResponseBlur/arrayParameter"))
                        .body(new String[]{"123456@qq.com", "123456@qq.com"}),
                new ParameterizedTypeReference<CustomizedResponse<String[]>>() {
                }
        ).getBody();
        assertNotNull(body);
        Arrays.stream(body.getData()).forEach(s -> assertEquals("1*****@qq.com", s));
    }

    /**
     * Tests the blurring of an Array return value.
     */
    @Test
    void blurArrayReturnValue() throws URISyntaxException {
        var body = restTemplate.exchange(
                RequestEntity.post(new URI("/customizedResponseBlur/arrayReturnValue"))
                        .body(new String[]{"123456@qq.com", "123456@qq.com"}),
                new ParameterizedTypeReference<CustomizedResponse<String[]>>() {
                }
        ).getBody();
        assertNotNull(body);
        Arrays.stream(body.getData()).forEach(s -> assertEquals("1*****@qq.com", s));
    }

    /**
     * Tests the blurring of an Object parameter.
     */
    @Test
    void blurObjectParameter() throws URISyntaxException {
        var body = restTemplate.exchange(
                RequestEntity.post(new URI("/customizedResponseBlur/objectParameter")).body(new Person("12345678910", "123456@qq.com")),
                new ParameterizedTypeReference<CustomizedResponse<Person>>() {
                }
        ).getBody();
        assertNotNull(body);
        var person = body.getData();
        assertEquals("123****8910", person.getPhoneNumber());
        assertEquals("1*****@qq.com", person.getEmail());
    }

    /**
     * Tests the blurring of an Object return value.
     */
    @Test
    void blurObjectReturnValue() throws URISyntaxException {
        var body = restTemplate.exchange(
                RequestEntity.post(new URI("/customizedResponseBlur/objectReturnValue")).body(new Person("12345678910", "123456@qq.com")),
                new ParameterizedTypeReference<CustomizedResponse<Person>>() {
                }
        ).getBody();
        assertNotNull(body);
        var person = body.getData();
        assertEquals("123****8910", person.getPhoneNumber());
        assertEquals("1*****@qq.com", person.getEmail());
    }
}
