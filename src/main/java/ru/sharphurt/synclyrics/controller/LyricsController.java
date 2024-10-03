package ru.sharphurt.synclyrics.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.sharphurt.synclyrics.lyricsapi.dto.LyricsResponseDto;
import ru.sharphurt.synclyrics.lyricsapi.service.LrcLibDataService;

@RestController
@RequestMapping("/lyrics")
@AllArgsConstructor
public class LyricsController {

    private final LrcLibDataService lrcLibDataService;

    @GetMapping("/get")
    public ResponseEntity<LyricsResponseDto> get(@RequestParam String trackName, @RequestParam String artistName, @RequestParam String albumName) {
        var result = lrcLibDataService.getLyrics(trackName, artistName, albumName);
        return ResponseEntity.ok(result);
    }
}
