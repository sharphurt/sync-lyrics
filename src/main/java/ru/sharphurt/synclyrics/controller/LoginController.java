package ru.sharphurt.synclyrics.controller;

import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sharphurt.synclyrics.auth.service.AuthorizationService;
import ru.sharphurt.synclyrics.constants.Template;

@RestController
@AllArgsConstructor
public class LoginController {

    private final AuthorizationService accessTokenService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("url", accessTokenService.getAuthorizationURL());
        return Template.LOGIN;
    }
}

