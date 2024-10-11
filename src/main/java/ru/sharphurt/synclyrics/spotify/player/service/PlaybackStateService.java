package ru.sharphurt.synclyrics.spotify.player.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sharphurt.synclyrics.exceptions.NoPlayingTrackException;
import ru.sharphurt.synclyrics.auth.dto.SpotifyTokenResponseDto;

import java.util.LinkedHashMap;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@Service
public class PlaybackStateService {

    @Value("${spotify.baseUri}")
    private String baseUri;

    public LinkedHashMap getCurrentTrackName(SpotifyTokenResponseDto accessTokenDto) {
        var url = baseUri + "/me/player/currently-playing";
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessTokenDto.getAccess_token());

        var entity = new HttpEntity<>("parameters", headers);

        var response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        if (response.getStatusCode() == NO_CONTENT) {
            throw new NoPlayingTrackException(null);
        }

        return (LinkedHashMap) response.getBody();
    }
}
