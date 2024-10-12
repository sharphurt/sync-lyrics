package ru.sharphurt.synclyrics.dto.spotify;

import lombok.Data;

@Data
public class ExplicitContentDto {

    private Boolean filter_enabled;
    private Boolean filter_locked;
}

