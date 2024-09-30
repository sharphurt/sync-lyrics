package ru.sharphurt.synclyrics.pkceauth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.sharphurt.synclyrics.pkceauth.dto.TokenDto;

@Service
@RequiredArgsConstructor
public class AccessTokenService {

    @Value("${spotify.tokenUri}")
    private String tokenUri;

    @Value("${spotify.clientId}")
    private String clientId;

    @Value("${spotify.redirectUri}")
    private String redirectUri;

    @Value("${spotify.authorizationGrandType}")
    private String authorizationGrandType;

    @Value("${spotify.clientSecret}")
    private String codeVerifier;

    public TokenDto getToken(String code) {
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        var map = new LinkedMultiValueMap<>();
        map.add("code", code);
        map.add("client_id", clientId);
        map.add("grant_type", authorizationGrandType);
        map.add("redirect_uri", redirectUri);
        map.add("code_verifier", codeVerifier);

        var request = new HttpEntity<>(map, headers);

        return restTemplate.postForObject(tokenUri, request, TokenDto.class);
    }
}
