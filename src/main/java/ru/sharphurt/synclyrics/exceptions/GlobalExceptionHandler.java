package ru.sharphurt.synclyrics.exceptions;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.sharphurt.synclyrics.dto.ControllerErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<ControllerErrorResponse> feignNotFoundExceptionHandler(FeignException.NotFound e) {
        return new ResponseEntity<>(ControllerErrorResponse.fromException(e), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LyricsProcessingException.class)
    public ResponseEntity<ControllerErrorResponse> feignNotFoundExceptionHandler(LyricsProcessingException e) {
        return new ResponseEntity<>(ControllerErrorResponse.fromException(e), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoPlayingTrackException.class)
    public ResponseEntity<ControllerErrorResponse> noPlayingTrackExceptionHandler(NoPlayingTrackException e) {
        return new ResponseEntity<>(ControllerErrorResponse.fromException(e), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(SessionNotFoundException.class)
    public ResponseEntity<ControllerErrorResponse> sessionNotFoundExceptionHandler(SessionNotFoundException e) {
        return new ResponseEntity<>(ControllerErrorResponse.fromException(e), HttpStatus.NO_CONTENT);
    }
}
