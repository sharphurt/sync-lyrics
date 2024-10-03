package ru.sharphurt.synclyrics.lyricsapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sharphurt.synclyrics.lyricsapi.dto.LyricsResponseDto;
import ru.sharphurt.synclyrics.lyricsapi.dto.TimedString;
import ru.sharphurt.synclyrics.lyricsapi.dto.TrackLyricsDto;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class LrcLibDataService {

    @Value("${lrclib.baseUri}")
    private String baseUri;

    public LyricsResponseDto getLyrics(String trackName, String artistName, String albumName) {
        var url = baseUri + "/search?q=%s %s %s".formatted(trackName, artistName, albumName);
        var restTemplate = new RestTemplate();


        var result = restTemplate.getForObject(url, TrackLyricsDto[].class);
        if (result.length > 0) {
            return parseTrackLyrics(result[0]);
        }

        return null;
    }

    private LyricsResponseDto parseTrackLyrics(TrackLyricsDto dto) {
        var result = new LyricsResponseDto();
        result.setId(dto.getId());
        result.setName(dto.getName());
        result.setInstrumental(dto.getInstrumental());
        result.setDuration(dto.getDuration());
        result.setArtistName(dto.getArtistName());
        result.setAlbumName(dto.getAlbumName());
        result.setTrackName(dto.getTrackName());

        if (dto.getSyncedLyrics() != null) {
            result.setSyncedLyrics(parseSyncedLyrics(dto.getSyncedLyrics()));
        }

        if (dto.getPlainLyrics() != null) {
            result.setPlainLyrics(Arrays.stream(dto.getPlainLyrics().split("\n")).toList());
        }

        return result;
    }

    private List<TimedString> parseSyncedLyrics(String lyrics) {
        var result = new ArrayList<TimedString>();
        var strings = lyrics.split("\n");

        for (var string : strings) {
            var time = string.substring(1, 9);
            var minutes = Integer.parseInt(time.substring(0, 2));
            var seconds = Integer.parseInt(time.substring(3, 5));
            var millis = Integer.parseInt(time.substring(6, 8));
            var duration = Duration.ofMinutes(minutes).plusSeconds(seconds).plusMillis(millis);

            var text = string.substring(10).trim();
            var timeMillis = duration.toMillis();

            result.add(new TimedString(duration, timeMillis, text));
        }

        return result;
    }
}
