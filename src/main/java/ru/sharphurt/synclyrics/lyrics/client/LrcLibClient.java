package ru.sharphurt.synclyrics.lyrics.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.sharphurt.synclyrics.lyrics.dto.TrackLyricsDto;

@FeignClient(name = "lrcLibClient", url = "${lrclib.url}")
public interface LrcLibClient {

    @GetMapping("/get")
    TrackLyricsDto getTrack(@RequestParam("artist_name") String artisName,
                            @RequestParam("track_name") String trackName,
                            @RequestParam("album_name") String albumName);
}
