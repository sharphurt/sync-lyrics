package ru.sharphurt.synclyrics.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sharphurt.synclyrics.dto.lyrics.LyricsRequestDto;
import ru.sharphurt.synclyrics.dto.lyrics.LyricsResponseDto;
import ru.sharphurt.synclyrics.service.lyrics.LrcLibDataService;

@RestController
@RequestMapping("/lyrics")
@AllArgsConstructor
@CrossOrigin
public class LyricsController {

    private final LrcLibDataService lrcLibDataService;

    @PostMapping("/get")
    public ResponseEntity<LyricsResponseDto> get(@RequestBody LyricsRequestDto requestDto) {
        var result = lrcLibDataService.getLyrics(requestDto);
        return ResponseEntity.ok(result);
    }
}
