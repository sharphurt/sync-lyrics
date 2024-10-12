package ru.sharphurt.synclyrics.exceptions;

import static ru.sharphurt.synclyrics.constants.AliasConstants.EXCEPTION_NO_PLAYING_TRACK;

public class NoPlayingTrackException extends BaseException {

    public NoPlayingTrackException() {
        super(EXCEPTION_NO_PLAYING_TRACK, new Throwable());
    }

    public NoPlayingTrackException(Throwable e) {
        super(EXCEPTION_NO_PLAYING_TRACK, e);
    }
}
