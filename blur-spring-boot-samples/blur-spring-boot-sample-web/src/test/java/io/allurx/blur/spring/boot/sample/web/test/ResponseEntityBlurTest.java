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

import io.allurx.blur.spring.boot.sample.web.controller.ResponseEntityBlurController;
import io.allurx.blur.spring.boot.sample.web.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;

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
 * Tests for the ResponseEntityBlurController.
 * This class verifies the data blurring functionalities for various input types using ResponseEntity.
 *
 * @author allurx
 * @see ResponseEntityBlurController
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ResponseEntityBlurTest {

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Tests the blurring of a String parameter.
     */
    @Test
    void blurStringParameter() {
        var body = restTemplate.getForObject("/responseEntityBlur/stringParameter?email={?}", String.class, "123456@qq.com");
        assertEquals("1*****@qq.com", body);
    }

    /**
     * Tests the blurring of a String return value.
     */
    @Test
    void blurStringReturnValue() {
        var body = restTemplate.getForObject("/responseEntityBlur/stringReturnValue?email={?}", String.class, "123456@qq.com");
        assertEquals("1*****@qq.com", body);
    }

    /**
     * Tests the blurring of a Collection parameter.
     */
    @Test
    void blurCollectionParameter() throws URISyntaxException {
        var body = restTemplate.exchange(
                RequestEntity.post(new URI("/responseEntityBlur/collectionParameter"))
                        .body(Stream.of("123456@qq.com", "123456@qq.com", "123456@qq.com").collect(Collectors.toList())),
                new ParameterizedTypeReference<List<String>>() {
                }
        ).getBody();
        assertNotNull(body);
        body.forEach(s -> assertEquals("1*****@qq.com", s));
    }

    /**
     * Tests the blurring of a Collection return value.
     */
    @Test
    void blurCollectionReturnValue() throws URISyntaxException {
        var body = restTemplate.exchange(
                RequestEntity.post(new URI("/responseEntityBlur/collectionReturnValue"))
                        .body(Stream.of("123456@qq.com", "123456@qq.com", "123456@qq.com").collect(Collectors.toList())),
                new ParameterizedTypeReference<List<String>>() {
                }
        ).getBody();
        assertNotNull(body);
        body.forEach(s -> assertEquals("1*****@qq.com", s));
    }

    /**
     * Tests the blurring of a Map parameter.
     */
    @Test
    void blurMapParameter() throws URISyntaxException {
        var map = Stream.of("allurx").collect(Collectors.toMap(s -> s, o -> new Person("12345678910", "123456@qq.com")));
        var body = restTemplate.exchange(
                RequestEntity.post(new URI("/responseEntityBlur/mapParameter")).body(map),
                new ParameterizedTypeReference<Map<String, Person>>() {
                }
        ).getBody();
        assertNotNull(body);
        body.forEach((s, person) -> {
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
                RequestEntity.post(new URI("/responseEntityBlur/mapReturnValue")).body(map),
                new ParameterizedTypeReference<Map<String, Person>>() {
                }
        ).getBody();
        assertNotNull(body);
        body.forEach((s, person) -> {
            assertEquals("a*****", s);
            assertEquals("123****8910", person.getPhoneNumber());
            assertEquals("1*****@qq.com", person.getEmail());
        });
    }

    /**
     * Tests the blurring of an Array parameter.
     */
    @Test
    void blurArrayParameter() {
        var body = restTemplate.postForObject("/responseEntityBlur/arrayParameter", new String[]{"123456@qq.com", "123456@qq.com"}, String[].class);
        Arrays.stream(body).forEach(s -> assertEquals("1*****@qq.com", s));
    }

    /**
     * Tests the blurring of an Array return value.
     */
    @Test
    void blurArrayReturnValue() {
        var body = restTemplate.postForObject("/responseEntityBlur/arrayReturnValue", new String[]{"123456@qq.com", "123456@qq.com"}, String[].class);
        Arrays.stream(body).forEach(s -> assertEquals("1*****@qq.com", s));
    }

    /**
     * Tests the blurring of an Object parameter.
     */
    @Test
    void blurObjectParameter() {
        var person = restTemplate.postForObject("/responseEntityBlur/objectParameter", new Person("12345678910", "123456@qq.com"), Person.class);
        assertEquals("123****8910", person.getPhoneNumber());
        assertEquals("1*****@qq.com", person.getEmail());
    }

    /**
     * Tests the blurring of an Object return value.
     */
    @Test
    void blurObjectReturnValue() {
        var person = restTemplate.postForObject("/responseEntityBlur/objectReturnValue", new Person("12345678910", "123456@qq.com"), Person.class);
        assertEquals("123****8910", person.getPhoneNumber());
        assertEquals("1*****@qq.com", person.getEmail());
    }

}
