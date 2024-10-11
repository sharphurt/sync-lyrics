package ru.sharphurt.synclyrics.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sharphurt.synclyrics.auth.dto.SessionDto;
import ru.sharphurt.synclyrics.constants.Template;
import ru.sharphurt.synclyrics.spotify.player.service.PlaybackStateService;
import ru.sharphurt.synclyrics.spotify.user.service.UserService;

@Controller
@AllArgsConstructor
public class MainController {

    private final UserService getUserInfoService;
    private final PlaybackStateService playbackStateService;

    @GetMapping("/main")
    public String showMain(HttpSession httpSession, Model model, HttpServletResponse response) {
        var session = (SessionDto) httpSession.getAttribute("session");
        if (session.isExpired()) {
            return "redirect:" + Template.LOGIN;
        }

        setUpCookies(session, response);

        var userInfo = getUserInfoService.getCurrentUserInfo(session.getAccessToken());
        model.addAttribute("userInfo", userInfo);
        return Template.MAIN;
    }

    private void setUpCookies(SessionDto sessionDto, HttpServletResponse response) {
        createCookie("accessToken", sessionDto.getAccessToken(), response);
        createCookie("expiresAt", sessionDto.getExpiresAt().toString(), response);
        createCookie("sessionId", sessionDto.getSessionId().toString(), response);
        createCookie("userId", sessionDto.getUserId(), response);
    }

    private static void createCookie(String name, String value, HttpServletResponse response) {
        var expiresAtCookie = new Cookie(name, value);
        expiresAtCookie.setMaxAge(3600);
        expiresAtCookie.setSecure(true);
        response.addCookie(expiresAtCookie);
    }
}
