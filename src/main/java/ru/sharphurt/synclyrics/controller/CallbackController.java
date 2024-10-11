package ru.sharphurt.synclyrics.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.sharphurt.synclyrics.constants.Template;
import ru.sharphurt.synclyrics.auth.service.AuthorizationService;

@Controller
@AllArgsConstructor
public class CallbackController {

    private final AuthorizationService accessTokenService;

    @GetMapping("/callback")
    public RedirectView callback(@RequestParam(value = "code", required = false) String code,
                                 @RequestParam(value = "error", required = false) String error,
                                 Model model, HttpSession httpSession) {
        if (error != null) {
            model.addAttribute("url", accessTokenService.getAuthorizationURL());
            return new RedirectView(Template.AUTHORIZATION_ERROR);
        }

        var session = accessTokenService.finishAuthorization(code);
        httpSession.setAttribute("session", session);

        var token = session.getAccessToken();
        httpSession.setAttribute("accessToken", token);
        return new RedirectView(Template.MAIN);
    }
}
