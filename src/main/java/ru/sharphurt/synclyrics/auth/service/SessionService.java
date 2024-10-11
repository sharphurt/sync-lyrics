package ru.sharphurt.synclyrics.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sharphurt.synclyrics.auth.dto.SessionDto;
import ru.sharphurt.synclyrics.auth.dto.SpotifyTokenResponseDto;
import ru.sharphurt.synclyrics.auth.entity.Session;
import ru.sharphurt.synclyrics.auth.repository.SessionRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionDto createSession(String userId, SpotifyTokenResponseDto tokenDto) {
        var session = Session.builder()
                .sessionId(UUID.randomUUID())
                .accessToken(tokenDto.getAccess_token())
                .accessExpiresAt(LocalDateTime.now().plusSeconds(tokenDto.getExpires_in()))
                .userId(userId)
                .refreshToken(tokenDto.getRefresh_token())
                .lastTokenUpdate(LocalDateTime.now())
                .build();

        var saved = sessionRepository.save(session);
        return SessionDto.builder()
                .accessToken(saved.getAccessToken())
                .expiresAt(saved.getAccessExpiresAt())
                .userId(saved.getUserId())
                .sessionId(saved.getSessionId())
                .build();
    }
}
