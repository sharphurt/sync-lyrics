package ru.sharphurt.synclyrics.dto.lyrics;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LyricsStringDto {
    private Long milliseconds;
    private String text;
}
