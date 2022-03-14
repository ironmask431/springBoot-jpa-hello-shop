package com.restApi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Controller
public class HomeController {

    @GetMapping
    public String home(Model model, Locale locale){

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 h시 m분 s초");
        String serverTime = now.format(formatter);
        model.addAttribute("serverTime",now);

        return "home";
    }
}
