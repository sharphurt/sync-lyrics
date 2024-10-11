package ru.sharphurt.synclyrics.exceptions;

import ru.sharphurt.synclyrics.lyrics.dto.LyricsRequestDto;

import static ru.sharphurt.synclyrics.constants.AliasConstants.EXCEPTION_NO_PLAYING_TRACK;

public class NoPlayingTrackException extends BaseException {

    public NoPlayingTrackException(LyricsRequestDto requestDto) {
        super(EXCEPTION_NO_PLAYING_TRACK, new Throwable());
    }

    public NoPlayingTrackException(LyricsRequestDto requestDto, Throwable e) {
        super(EXCEPTION_NO_PLAYING_TRACK, e);
    }
}
