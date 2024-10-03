package ru.sharphurt.synclyrics.lyricsapi.dto;

import lombok.Data;

@Data
public class LyricsRequestDto {
    private String trackName;
    private String artistName;
    private String albumName;
}
