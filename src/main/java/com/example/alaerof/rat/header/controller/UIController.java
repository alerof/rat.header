package com.example.alaerof.rat.header.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/")
public class UIController {
    private final Random rnd = new Random();
    @Value("${app.callback.url}")
    private String redirectUri;

    @GetMapping("/login")
    public String indexHtml(@RequestParam(value = "message", required = false, defaultValue = "Rat") String message,
                            @RequestParam(value = "redirect_uri", required = false) String redirect,
                            @RequestParam(value = "state", required = false) String state,
                            Model model) {
        log.info("message: {}", message);
        log.info("redirect_uri: {}", redirect);
        log.info("state: {}", state);
        model.addAttribute("message", message);
        if (redirect == null) {
            redirect = redirectUri;
        }
        model.addAttribute("url", redirect);
        var code = UUID.randomUUID().toString();
        model.addAttribute("code", code);
        model.addAttribute("state", state);
        return "login";
    }
}

