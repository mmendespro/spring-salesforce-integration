package br.com.sfclient.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class GreetingController {

    @GetMapping(value = "/api/hello", produces = "application/txt")
    public String greet(@RequestParam(required=false, defaultValue = "world") String name) {
        log.info("greet() called");
        return String.format("Hello %s!", name);
    }
}
