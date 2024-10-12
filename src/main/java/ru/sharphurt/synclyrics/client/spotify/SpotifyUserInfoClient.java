package ru.sharphurt.synclyrics.client.spotify;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.sharphurt.synclyrics.dto.spotify.UserDto;

@FeignClient(name = "user-info", url = "${spotify.baseUri}")
public interface SpotifyUserInfoClient {

    @GetMapping("/me")
    UserDto getCurrentUser(@RequestHeader("Authorization") String accessToken);
}
