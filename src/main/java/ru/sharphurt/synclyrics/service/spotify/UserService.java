package ru.sharphurt.synclyrics.service.spotify;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sharphurt.synclyrics.client.spotify.SpotifyUserInfoClient;
import ru.sharphurt.synclyrics.dto.spotify.UserDto;

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
