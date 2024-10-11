package ru.sharphurt.synclyrics.spotify.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sharphurt.synclyrics.spotify.user.client.SpotifyUserInfoClient;
import ru.sharphurt.synclyrics.spotify.user.dto.UserDto;

@Service
@RequiredArgsConstructor
public class UserService {

    private final SpotifyUserInfoClient spotifyUserInfoClient;

    public UserDto getCurrentUserInfo(String accessToken) {
        return spotifyUserInfoClient.getCurrentUser("Bearer " + accessToken);
    }

    public String getCurrentUserId(String accessToken) {
        return getCurrentUserInfo(accessToken).getId();
    }
}
