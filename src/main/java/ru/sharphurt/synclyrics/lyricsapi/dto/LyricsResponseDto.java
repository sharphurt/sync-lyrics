package ru.sharphurt.synclyrics.lyricsapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class LyricsResponseDto {
    private Integer id;
    private String name;
    private String trackName;
    private String artistName;
    private String albumName;
    private Integer duration;
    private Boolean instrumental;
    private List<String> plainLyrics;
    private List<TimedString> syncedLyrics;
}
