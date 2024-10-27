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

package io.allurx.blur.spring.boot.sample.web.controller;

import io.allurx.annotation.parser.type.Cascade;
import io.allurx.blur.annotation.Email;
import io.allurx.blur.annotation.Name;
import io.allurx.blur.spring.boot.sample.web.model.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Controller for handling data blurring of {@link ResponseEntity} types.
 * This controller provides endpoints to demonstrate the blurring functionality
 * for various input and output types, including strings, collections, maps, arrays, and objects.
 *
 * @author allurx
 */
@RestController
@RequestMapping("/responseEntityBlur")
public class ResponseEntityBlurController {

    /**
     * Default constructor
     */
    public ResponseEntityBlurController() {
    }

    /**
     * Blurs a string parameter annotated with {@link Email}.
     *
     * @param email the email address to blur
     * @return a {@link ResponseEntity} containing the blurred email
     */
    @GetMapping("/stringParameter")
    public ResponseEntity<String> blurStringParameter(@RequestParam @Email String email) {
        return ok(email);
    }

    /**
     * Blurs a string return value annotated with {@link Email}.
     *
     * @param email the email address to process
     * @return a {@link ResponseEntity} containing the blurred email
     */
    @GetMapping("/stringReturnValue")
    public ResponseEntity<@Email String> blurStringReturnValue(@RequestParam String email) {
        return ok(email);
    }

    /**
     * Blurs a collection of email addresses provided in the request body.
     *
     * @param emails a list of email addresses to blur
     * @return a {@link ResponseEntity} containing the blurred emails
     */
    @PostMapping("/collectionParameter")
    public ResponseEntity<List<String>> blurCollectionParameter(@RequestBody List<@Email String> emails) {
        return ok(emails);
    }

    /**
     * Blurs a collection of email addresses in the response body.
     *
     * @param emails a list of email addresses to process
     * @return a {@link ResponseEntity} containing the blurred emails
     */
    @PostMapping("/collectionReturnValue")
    public ResponseEntity<List<@Email String>> blurCollectionReturnValue(@RequestBody List<String> emails) {
        return ok(emails);
    }

    /**
     * Blurs a map with keys annotated with {@link Name} and values of type {@link Person}.
     *
     * @param map a map of names to persons to blur
     * @return a {@link ResponseEntity} containing the blurred map
     */
    @PostMapping("/mapParameter")
    public ResponseEntity<Map<String, Person>> blurMapParameter(@RequestBody Map<@Name String, @Cascade Person> map) {
        return ok(map);
    }

    /**
     * Blurs a map with keys of type {@link String} and values of type {@link Person}.
     *
     * @param map a map of names to persons to process
     * @return a {@link ResponseEntity} containing the blurred map
     */
    @PostMapping("/mapReturnValue")
    public ResponseEntity<Map<@Name String, @Cascade Person>> blurMapReturnValue(@RequestBody Map<String, Person> map) {
        return ok(map);
    }

    /**
     * Blurs an array of email addresses provided in the request body.
     *
     * @param array an array of email addresses to blur
     * @return a {@link ResponseEntity} containing the blurred emails
     */
    @PostMapping("/arrayParameter")
    public ResponseEntity<String[]> blurArrayParameter(@RequestBody @Email String[] array) {
        return ok(array);
    }

    /**
     * Blurs an array of email addresses in the response body.
     *
     * @param array an array of email addresses to process
     * @return a {@link ResponseEntity} containing the blurred emails
     */
    @PostMapping("/arrayReturnValue")
    public ResponseEntity<@Email String[]> blurArrayReturnValue(@RequestBody String[] array) {
        return ok(array);
    }

    /**
     * Blurs a {@link Person} object annotated with {@link Cascade} in the request body.
     *
     * @param person the person object to blur
     * @return a {@link ResponseEntity} containing the blurred person
     */
    @PostMapping("/objectParameter")
    public ResponseEntity<Person> blurObjectParameter(@RequestBody @Cascade Person person) {
        return ok(person);
    }

    /**
     * Blurs a {@link Person} object in the response body.
     *
     * @param person the person object to process
     * @return a {@link ResponseEntity} containing the blurred person
     */
    @PostMapping("/objectReturnValue")
    public ResponseEntity<@Cascade Person> blurObjectReturnValue(@RequestBody Person person) {
        return ok(person);
    }

}
