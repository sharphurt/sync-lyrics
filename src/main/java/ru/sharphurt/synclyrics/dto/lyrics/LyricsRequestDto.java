package ru.sharphurt.synclyrics.dto.lyrics;

import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class LyricsRequestDto {
    @NonNull
    private String trackName;

    @NonNull
    private String artistName;

    @NonNull
    private String albumName;
}
