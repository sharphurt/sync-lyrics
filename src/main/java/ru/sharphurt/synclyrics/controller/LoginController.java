package ru.sharphurt.synclyrics.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sharphurt.synclyrics.constants.Template;
import ru.sharphurt.synclyrics.pkceauth.service.AuthorizationUrlService;

@Controller
@AllArgsConstructor
public class LoginController {

    private final AuthorizationUrlService authorizationUrlService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("url", authorizationUrlService.getAuthorizationURL());
        return Template.LOGIN;
    }
}

