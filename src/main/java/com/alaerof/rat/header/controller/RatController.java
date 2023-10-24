package com.alaerof.rat.header.controller;

import com.alaerof.rat.header.model.Rat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
public class RatController {
    private final AtomicInteger counter = new AtomicInteger();

    @GetMapping("/rat")
    public Rat greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        log.info("name: {}", name);
        return Rat.builder()
                .id(counter.incrementAndGet())
                .content(name)
                .build();
    }

    @PostMapping("/auth/token")
    public Rat token(@RequestParam(value = "code") String code,
                     @RequestParam(value = "state", required = false) String state) {
        log.info("code: {}", code);
        log.info("state: {}", state);
        return Rat.builder()
                .id(counter.incrementAndGet())
                .content(code)
                .build();
    }

    @GetMapping("/callback")
    public void callback(@RequestParam(value = "code") String code,
                     @RequestParam(value = "state") String state) {
        log.info("code: {}", code);
        log.info("state: {}", state);

    }
}
