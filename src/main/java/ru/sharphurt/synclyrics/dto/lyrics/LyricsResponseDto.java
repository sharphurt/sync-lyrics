package ru.sharphurt.synclyrics.dto.lyrics;

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
    private List<LyricsStringDto> plainLyrics;
    private List<LyricsStringDto> syncedLyrics;
}
