package com.basic.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Slf4j
@Controller
public class HomeController {
    @GetMapping
    public String home(Model model, Locale locale){
        log.info("Welcome home! The client locale is "+locale+".");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년M월d일(E) a h시m분s초");
        String format_now = now.format(formatter);
        log.info("format_now="+format_now);
        model.addAttribute("serverTime",format_now);
        return "home";
    }
}
