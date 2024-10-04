package ru.sharphurt.synclyrics.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.sharphurt.synclyrics.spotifyapi.player.exception.NoPlayingTrackException;
import ru.sharphurt.synclyrics.spotifyapi.player.service.PlaybackStateService;
import ru.sharphurt.synclyrics.spotifyapi.user.service.GetUserInfoService;
import ru.sharphurt.synclyrics.constants.Template;
import ru.sharphurt.synclyrics.pkceauth.dto.TokenDto;

@Controller
@AllArgsConstructor
public class MainController {

    private final GetUserInfoService getUserInfoService;
    private final PlaybackStateService playbackStateService;

    @GetMapping("/main")
    public RedirectView showMain(HttpSession httpSession, Model model, HttpServletResponse response) {
        var tokenDto = (TokenDto) httpSession.getAttribute("accessToken");
        if (tokenDto == null) {
            return new RedirectView(Template.INDEX);
        }

        setUpCookies(tokenDto, response);

        model.addAttribute("accessToken", tokenDto);

        var userInfo = getUserInfoService.getCurrentUserInfo(tokenDto);
        model.addAttribute("userInfo", userInfo);

        try {
            var playbackState = playbackStateService.getCurrentTrackName(tokenDto);
            model.addAttribute("currentPlaying", playbackState);
            model.addAttribute("display", 1);
        } catch (NoPlayingTrackException exception) {
            model.addAttribute("display", 0);
        }

        return new RedirectView(Template.MAIN);
    }

    private void setUpCookies(TokenDto tokenDto, HttpServletResponse response) {
        var accessTokenCookie = new Cookie("accessToken", tokenDto.getAccess_token());
        accessTokenCookie.setMaxAge(tokenDto.getExpires_in());
        accessTokenCookie.setSecure(true);

        var expiresAtCookie = new Cookie("expiresAt", tokenDto.getExpires_in().toString());
        var refreshTokenCookie = new Cookie("refreshToken", tokenDto.getRefresh_token());
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge(tokenDto.getExpires_in());

        response.addCookie(accessTokenCookie);
        response.addCookie(expiresAtCookie);
        response.addCookie(refreshTokenCookie);
    }
}
