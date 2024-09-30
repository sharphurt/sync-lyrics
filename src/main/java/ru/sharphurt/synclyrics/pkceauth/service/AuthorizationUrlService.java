package ru.sharphurt.synclyrics.pkceauth.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.sharphurt.synclyrics.pkceauth.util.CryptographicUtil;

@Service
@Data
public class AuthorizationUrlService {
    @Value("${spotify.authorizationUri}")
    private String authorizationUri;

    @Value("${spotify.clientId}")
    private String clientId;

    @Value("${spotify.redirectUri}")
    private String redirectUri;

    @Value("${spotify.scopes}")
    private String scopes;

    @Value("${spotify.codeVerifier}")
    private String codeVerifier;

    public String getAuthorizationURL() {
        var codeChallenge = CryptographicUtil.generateCodeChallenge(codeVerifier);

        return authorizationUri
                + "?client_id=" + clientId
                + "&response_type=code&redirect_uri=" + redirectUri
                + "&code_challenge_method=S256&code_challenge=" + codeChallenge
                + "&scope=" + scopes;
    }
}
