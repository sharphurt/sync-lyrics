package ru.sharphurt.synclyrics.api.user.dto;

import lombok.Data;

@Data
public class ExplicitContentDto {

    private Boolean filter_enabled;
    private Boolean filter_locked;
}

