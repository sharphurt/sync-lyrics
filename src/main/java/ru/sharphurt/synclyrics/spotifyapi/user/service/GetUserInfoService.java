package ru.sharphurt.synclyrics.spotifyapi.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sharphurt.synclyrics.spotifyapi.user.dto.UserDto;
import ru.sharphurt.synclyrics.pkceauth.dto.TokenDto;

@Service
public class GetUserInfoService {

    @Value("${spotify.baseUri}")
    private String baseUri;

    public UserDto getCurrentUserInfo(TokenDto accessToken) {
        var url = baseUri + "/me";
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken.getAccess_token());

        var entity = new HttpEntity<>("parameters", headers);

        var response = restTemplate.exchange(url, HttpMethod.GET, entity, UserDto.class);
        return response.getBody();
    }

    public String getUsername(TokenDto accessTokenDto) {
        return getCurrentUserInfo(accessTokenDto).getDisplay_name();
    }
}
