package com.tisory.jaimemin.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jaime
 * @title SampleController
 * @see\n <pre>
 * </pre>
 * @since 2022-05-02
 */
@RestController
@RequestMapping("/api")
public class SampleController {

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }
}
