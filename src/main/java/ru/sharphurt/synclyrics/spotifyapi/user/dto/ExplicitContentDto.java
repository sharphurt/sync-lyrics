package ru.sharphurt.synclyrics.spotifyapi.user.dto;

import lombok.Data;

@Data
public class ExplicitContentDto {

    private Boolean filter_enabled;
    private Boolean filter_locked;
}

