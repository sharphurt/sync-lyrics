package ru.sharphurt.synclyrics.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sharphurt.synclyrics.dto.auth.SessionDto;
import ru.sharphurt.synclyrics.service.auth.SessionService;

@RestController
@RequestMapping("session/")
@RequiredArgsConstructor
@CrossOrigin
public class SessionController {

    private final SessionService sessionService;

    @GetMapping("/get/{uuid}")
    public ResponseEntity<SessionDto> getSession(@PathVariable String uuid) {
        var session = sessionService.getSession(uuid);
        return ResponseEntity.ok(session);
    }
}
