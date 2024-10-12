package ru.sharphurt.synclyrics.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import ru.sharphurt.synclyrics.client.auth.SpotifyAuthClient;
import ru.sharphurt.synclyrics.dto.auth.SessionDto;
import ru.sharphurt.synclyrics.dto.spotify.SpotifyTokenResponseDto;
import ru.sharphurt.synclyrics.util.CryptographicUtil;
import ru.sharphurt.synclyrics.service.spotify.UserService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    @Value("${spotify.authorizationUri}")
    private String authorizationUri;

    @Value("${spotify.clientId}")
    private String clientId;

    @Value("${spotify.redirectUri}")
    private String redirectUri;

    @Value("${spotify.grandType}")
    private String grantType;

    @Value("${spotify.clientSecret}")
    private String codeVerifier;

    @Value("${spotify.scopes}")
    private String scopes;

    private final SpotifyAuthClient spotifyAuthClient;
    private final SessionService sessionService;
    private final UserService userService;

    public String getAuthorizationURL() {
        var codeChallenge = CryptographicUtil.generateCodeChallenge(codeVerifier);
        return authorizationUri
                + "?client_id=" + clientId
                + "&response_type=code&redirect_uri=" + redirectUri
                + "&code_challenge_method=S256&code_challenge=" + codeChallenge
                + "&scope=" + scopes;
    }

    public SessionDto finishAuthorization(String uuid, String code) {
        var token = getToken(code);
        var userId = userService.getCurrentUserId(token.getAccess_token());
        return sessionService.createSession(uuid, userId, token);
    }

    private SpotifyTokenResponseDto getToken(String code) {
        var headers = new LinkedMultiValueMap<String, String>();
        headers.setAll(Map.of(
                "client_id", clientId,
                "grant_type", grantType,
                "code_verifier", codeVerifier,
                "redirect_uri", redirectUri,
                "code", code
        ));

        return spotifyAuthClient.getAccessToken(headers);
    }
}
