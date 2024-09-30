package ru.sharphurt.synclyrics.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sharphurt.synclyrics.api.player.exception.NoPlayingTrackException;
import ru.sharphurt.synclyrics.api.player.service.PlaybackStateService;
import ru.sharphurt.synclyrics.api.user.service.GetUserInfoService;
import ru.sharphurt.synclyrics.constants.Template;
import ru.sharphurt.synclyrics.pkceauth.dto.TokenDto;

@Controller
@AllArgsConstructor
public class MainController {

    private final GetUserInfoService getUserInfoService;
    private final PlaybackStateService playbackStateService;

    @GetMapping("/main")
    public String showMain(HttpSession httpSession, Model model) {
        var accessToken = (TokenDto) httpSession.getAttribute("accessToken");
        model.addAttribute("accessToken", accessToken);

        var userInfo = getUserInfoService.getCurrentUserInfo(accessToken);
        model.addAttribute("userName", userInfo.getDisplay_name());

        try {
            var playbackState = playbackStateService.getCurrentTrackName(accessToken);
            model.addAttribute("currentPlaying", playbackState);
            model.addAttribute("display", 1);
        } catch (NoPlayingTrackException exception) {
            model.addAttribute("display", 0);
        }

        return Template.MAIN;
    }
}
