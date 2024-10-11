package ru.sharphurt.synclyrics.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sharphurt.synclyrics.auth.service.AuthorizationService;
import ru.sharphurt.synclyrics.constants.Template;

@Controller
@AllArgsConstructor
public class IndexController {

    private final AuthorizationService accessTokenService;

    @GetMapping(value = {"/", "/index"} )
    public String index(Model model) {
        model.addAttribute("url", accessTokenService.getAuthorizationURL());
        return Template.INDEX;
    }
}
