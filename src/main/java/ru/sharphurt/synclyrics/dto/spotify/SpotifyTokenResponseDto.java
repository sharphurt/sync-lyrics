package ru.sharphurt.synclyrics.dto.spotify;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SpotifyTokenResponseDto {

    private String access_token;
    private String token_type;
    private String scope;
    private Integer expires_in;
    private String refresh_token;
}