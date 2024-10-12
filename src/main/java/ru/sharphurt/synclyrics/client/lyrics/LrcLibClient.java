package ru.sharphurt.synclyrics.client.lyrics;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.sharphurt.synclyrics.dto.lyrics.TrackLyricsDto;

@FeignClient(name = "lrcLibClient", url = "${lrclib.url}")
public interface LrcLibClient {

    @GetMapping("/get")
    TrackLyricsDto getTrack(@RequestParam("artist_name") String artisName,
                            @RequestParam("track_name") String trackName,
                            @RequestParam("album_name") String albumName);
}
