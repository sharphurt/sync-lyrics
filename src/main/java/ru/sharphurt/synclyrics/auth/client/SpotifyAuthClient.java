package ru.sharphurt.synclyrics.auth.client;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.sharphurt.synclyrics.auth.dto.SpotifyTokenResponseDto;

@FeignClient(name = "spotify-account-client", url = "${spotify.tokenUri}")
public interface SpotifyAuthClient {

    @PostMapping
    @Headers("Content-Type: application/x-www-form-urlencoded")
    SpotifyTokenResponseDto getAccessToken(@RequestBody MultiValueMap<String, String> body);
}
