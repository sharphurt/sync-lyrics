package ru.sharphurt.synclyrics.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sharphurt.synclyrics.service.auth.AuthorizationService;

@Controller
@RequiredArgsConstructor
public class CallbackController {

    private final AuthorizationService accessTokenService;

    @Value("${frontend.url}")
    private String frontendUrl;

    @GetMapping("/callback")
    public String callback(@RequestParam(value = "code", required = false) String code,
                           @RequestParam(value = "error", required = false) String error,
                           HttpSession httpSession) {
        if (error != null) {
            return getErrorRedirectUrl(error);
        }

        var uuid = (String) httpSession.getAttribute("uuid");
        var session = accessTokenService.finishAuthorization(uuid, code);
        httpSession.setAttribute("session", session);

        return getSuccessRedirectUrl();
    }

    private String getSuccessRedirectUrl() {
        return "redirect:%s/callback?state=success".formatted(frontendUrl);
    }

    private String getErrorRedirectUrl(String error) {
            return "redirect:%s/callback?state=error&error=%s".formatted(frontendUrl, error);
    }
}
