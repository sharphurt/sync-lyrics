package ru.sharphurt.synclyrics.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.sharphurt.synclyrics.constants.Template;
import ru.sharphurt.synclyrics.pkceauth.service.AccessTokenService;
import ru.sharphurt.synclyrics.pkceauth.service.AuthorizationUrlService;

@Controller
@AllArgsConstructor
public class CallbackController {

    private final AuthorizationUrlService authorizationUrlService;
    private final AccessTokenService accessTokenService;

    @GetMapping("/callback")
    public RedirectView callback(@RequestParam(value = "code", required = false) final String code,
                           @RequestParam(value = "error", required = false) final String error,
                           Model model, HttpSession session) {

        if (error != null) {
            model.addAttribute("url", authorizationUrlService.getAuthorizationURL());
            return new RedirectView(Template.AUTHORIZATION_ERROR);
        }

        session.setAttribute("code", code);
        var token = accessTokenService.getToken(code);
        session.setAttribute("accessToken", token);
        return new RedirectView(Template.MAIN);
    }
}
