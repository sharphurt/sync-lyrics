package ru.sharphurt.synclyrics.lyricsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;

@Data
@AllArgsConstructor
public class LyricsString {
    private Duration time;
    private Long milliseconds;
    private String text;
}
