package ru.sharphurt.synclyrics.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test")
    public String test(HttpSession httpSession) {
        var accessToken = httpSession.getAttribute("accessToken");

        return "test";
    }
}
