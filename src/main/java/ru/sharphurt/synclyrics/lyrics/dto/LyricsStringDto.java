package ru.sharphurt.synclyrics.lyrics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LyricsStringDto {
    private Long milliseconds;
    private String text;
}
