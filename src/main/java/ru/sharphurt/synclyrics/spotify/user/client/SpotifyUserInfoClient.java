package ru.sharphurt.synclyrics.spotify.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.sharphurt.synclyrics.spotify.user.dto.UserDto;

@FeignClient(name = "user-info", url = "${spotify.baseUri}")
public interface SpotifyUserInfoClient {

    @GetMapping("/me")
    UserDto getCurrentUser(@RequestHeader("Authorization") String accessToken);
}
