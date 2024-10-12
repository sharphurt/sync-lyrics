package ru.sharphurt.synclyrics.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sharphurt.synclyrics.dto.auth.SessionDto;
import ru.sharphurt.synclyrics.dto.spotify.SpotifyTokenResponseDto;
import ru.sharphurt.synclyrics.entity.Session;
import ru.sharphurt.synclyrics.exceptions.SessionNotFoundException;
import ru.sharphurt.synclyrics.repository.auth.SessionRepository;

import java.util.UUID;

import static ru.sharphurt.synclyrics.mapper.SessionMapper.SESSION_MAPPER;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionDto createSession(String uuid, String userId, SpotifyTokenResponseDto tokenDto) {
        var session = new Session(uuid, userId, tokenDto);
        var saved = sessionRepository.save(session);
        return SESSION_MAPPER.map(saved);
    }

    public SessionDto getSession(String uuid) {
        var session = sessionRepository.findBySessionIdAndHasFetchedByUuidIsFalse(UUID.fromString(uuid))
                .orElseThrow(SessionNotFoundException::new);

        session.setHasFetchedByUuid(true);
        sessionRepository.save(session);

        return SESSION_MAPPER.map(session);
    }
}
