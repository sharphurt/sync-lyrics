package ru.sharphurt.synclyrics.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.sharphurt.synclyrics.service.auth.AuthorizationService;

@Controller
@AllArgsConstructor
public class LoginController {

    private final AuthorizationService accessTokenService;

    @GetMapping("/login/{code}")
    public String login(@PathVariable String code, HttpSession session) {
        session.setAttribute("uuid", code);
        return "redirect:" + accessTokenService.getAuthorizationURL();
    }
}

