package ru.sharphurt.synclyrics.dto.lyrics;

import lombok.Data;

@Data
public class TrackLyricsDto {
     private Integer id;
     private String name;
     private String trackName;
     private String artistName;
     private String albumName;
     private Integer duration;
     private Boolean instrumental;
     private String plainLyrics;
     private String syncedLyrics;
}

