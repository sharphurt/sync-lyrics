package ru.sharphurt.synclyrics.exceptions;

import ru.sharphurt.synclyrics.dto.lyrics.LyricsRequestDto;

import static ru.sharphurt.synclyrics.constants.AliasConstants.EXCEPTION_LYRICS_PROCESSING;

public class LyricsProcessingException extends RuntimeException {

    public LyricsProcessingException(LyricsRequestDto requestDto) {
        super(EXCEPTION_LYRICS_PROCESSING.formatted(requestDto.getTrackName(), requestDto.getArtistName(), requestDto.getAlbumName()), new Throwable());
    }

    public LyricsProcessingException(LyricsRequestDto requestDto, Throwable e) {
        super(EXCEPTION_LYRICS_PROCESSING.formatted(requestDto.getTrackName(), requestDto.getArtistName(), requestDto.getAlbumName()), e);
    }
}
