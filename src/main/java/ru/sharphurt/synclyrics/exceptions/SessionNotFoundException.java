package ru.sharphurt.synclyrics.exceptions;

import static ru.sharphurt.synclyrics.constants.AliasConstants.EXCEPTION_SESSION_NOT_FOUND;

public class SessionNotFoundException extends BaseException {
    public SessionNotFoundException() {
        super(EXCEPTION_SESSION_NOT_FOUND, new Throwable());
    }

    public SessionNotFoundException(Throwable e) {
        super(EXCEPTION_SESSION_NOT_FOUND, e);
    }
}
