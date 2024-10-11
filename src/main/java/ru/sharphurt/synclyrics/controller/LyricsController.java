package ru.sharphurt.synclyrics.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sharphurt.synclyrics.lyrics.dto.LyricsRequestDto;
import ru.sharphurt.synclyrics.lyrics.dto.LyricsResponseDto;
import ru.sharphurt.synclyrics.lyrics.service.LrcLibDataService;

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
