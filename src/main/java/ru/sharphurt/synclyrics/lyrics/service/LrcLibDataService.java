package ru.sharphurt.synclyrics.lyrics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sharphurt.synclyrics.lyrics.client.LrcLibClient;
import ru.sharphurt.synclyrics.lyrics.dto.LyricsRequestDto;
import ru.sharphurt.synclyrics.lyrics.dto.LyricsResponseDto;
import ru.sharphurt.synclyrics.lyrics.dto.LyricsStringDto;
import ru.sharphurt.synclyrics.lyrics.dto.TrackLyricsDto;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LrcLibDataService {

    private final LrcLibClient lrcLibClient;

    public LyricsResponseDto getLyrics(LyricsRequestDto requestDto) {
        var result = lrcLibClient.getTrack(requestDto.getArtistName(), requestDto.getTrackName(), requestDto.getAlbumName());
        if (result != null) {
            return parseTrackLyrics(result);
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
            var plainLyrics = Arrays.stream(dto.getPlainLyrics().split("\n")).map(e -> new LyricsStringDto(null, e)).toList();
            result.setPlainLyrics(plainLyrics);
        }

        return result;
    }

    private List<LyricsStringDto> parseSyncedLyrics(String lyrics) {
        var result = new ArrayList<LyricsStringDto>();
        var strings = lyrics.split("\n");

        for (var string : strings) {
            var time = string.substring(string.indexOf('[') + 1, string.indexOf(']'));
            var minutes = Integer.parseInt(time.substring(0, 2));
            var seconds = Integer.parseInt(time.substring(3, 5));
            var millis = Integer.parseInt(time.substring(6, 8));
            var duration = Duration.ofMinutes(minutes).plusSeconds(seconds).plusMillis(millis);

            var text = string.trim().substring(string.indexOf(']') + 1).trim();
            result.add(new LyricsStringDto(duration.toMillis(), text));
        }

        return result;
    }
}
