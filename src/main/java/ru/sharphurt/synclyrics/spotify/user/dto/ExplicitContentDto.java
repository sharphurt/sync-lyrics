package ru.sharphurt.synclyrics.spotify.user.dto;

import lombok.Data;

@Data
public class ExplicitContentDto {

    private Boolean filter_enabled;
    private Boolean filter_locked;
}

